package com.ulfric.monty;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.iterators.ReverseListIterator;

import com.ulfric.commons.collection.ListHelper;
import com.ulfric.commons.collection.MapHelper;
import com.ulfric.monty.customize.Options;
import com.ulfric.monty.customize.OptionsService;
import com.ulfric.monty.element.Element;
import com.ulfric.monty.text.StandardStyles;
import com.ulfric.monty.text.Style;
import com.ulfric.monty.text.Text;
import com.ulfric.servix.services.locale.Locale;
import com.ulfric.servix.services.locale.LocaleService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public final class Scoreboard { // TODO element priorities

	private static final String DUMMY_CRITERIA = "dummy";
	private static final ConcurrentMap<UUID, Scoreboard> SCOREBOARDS = MapHelper.newConcurrentMap(2);

	public static Scoreboard create(Player player) {
		Objects.requireNonNull(player, "player");

		org.bukkit.scoreboard.Scoreboard scoreboard = ScoreboardHelper.getNewBukkitScoreboard();
		Locale locale = LocaleService.get().getLocale(player.getLocale());
		Options options = OptionsService.get().getOptions(player.getUniqueId());

		Scoreboard created = new Scoreboard(scoreboard, locale, options);
		register(player, created);
		return created;
	}

	private static void register(Player player, Scoreboard scoreboard) {
		scoreboard.players.add(player);
		Scoreboard old = SCOREBOARDS.put(player.getUniqueId(), scoreboard);
		if (old != null) {
			old.players.remove(player);
		}
		player.setScoreboard(scoreboard.scoreboard);
	}

	public static void clearScoreboard(Player player) {
		Objects.requireNonNull(player, "player");

		Scoreboard scoreboard = SCOREBOARDS.remove(player.getUniqueId());
		scoreboard.players.remove(player);
	}

	public static Optional<Scoreboard> getScoreboard(Player player) {
		return Optional.ofNullable(SCOREBOARDS.get(player.getUniqueId()));
	}

	static Collection<Scoreboard> getAllScoreboardsMutableView() { // TODO better solution
		return SCOREBOARDS.values();
	}

	private final org.bukkit.scoreboard.Scoreboard scoreboard;
	private final Objective objective;
	private final Options options;
	private final List<Entry> entries = new ArrayList<>();
	private final List<Player> players = new ArrayList<>();
	private final Set<Class<? extends Element>> update = SetUtils.newIdentityHashSet();
	private final Set<Element> removed = SetUtils.newIdentityHashSet();

	private Scoreboard(org.bukkit.scoreboard.Scoreboard scoreboard, Locale locale, Options options) {
		this.scoreboard = scoreboard;
		this.options = options;

		String title = locale.getMessage("scoreboard-title");
		this.objective = scoreboard.registerNewObjective(title, DUMMY_CRITERIA);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public void add(Element element) {
		Objects.requireNonNull(element, "element");

		entries.add(new Entry(element));
		Collections.sort(entries);
		queueUpdate(element.getClass());
	}

	public void remove(Element element) {
		Objects.requireNonNull(element, "element");

		removed.add(element);
	}

	public void queueUpdate(Class<? extends Element> element) {
		Objects.requireNonNull(element, "element");

		update.add(element);
	}

	public void update() {
		if (!needsUpdate()) {
			return;
		}

		Player agent = getPlayerForUpdate();

		if (agent == null) {
			return;
		}

		update(agent);
	}

	private void update(Player agent) {
		processRemovals();
		processUpdates(agent);
		updateScores();
	}

	private void processUpdates(Player agent) {
		Style style = getStyle();
		for (Entry entry : entries) {
			for (Class<? extends Element> update : this.update) {
				Element element = entry.element;
				if (!update.isInstance(element)) {
					continue;
				}

				Text text = element.apply(agent);
				List<String> content = style.apply(text);
				entry.displayContent(content);

				break;
			}
		}
		update.clear();
	}

	private Style getStyle() {
		Style style = options.getStyle();
		return style == null ? StandardStyles.defaultStyle() : style;
	}

	private void processRemovals() {
		removed.forEach(this::processRemoval);
		removed.clear();
	}

	private void processRemoval(Element removed) {
		Iterator<Entry> entries = this.entries.iterator();
		while (entries.hasNext()) {
			Entry entry = entries.next();
			if (entry.element == removed) {
				entries.remove();
				entry.unregister();
			}
		}
	}

	private void updateScores() {
		int score = 0;
		Iterator<Entry> entries = new ReverseListIterator<>(this.entries);
		while (entries.hasNext()) {
			Entry entry = entries.next();

			Iterator<Row> rows = new ReverseListIterator<>(entry.rows);
			while (rows.hasNext()) {
				Row row = rows.next();
				row.setScore(score++);
			}
		}
	}

	private boolean needsUpdate() {
		return !update.isEmpty() || !removed.isEmpty();
	}

	private Player getPlayerForUpdate() {
		return players.isEmpty() ? null : players.get(0);
	}

	private final class Entry implements Comparable<Entry> {
		private final Element element;
		final List<Row> rows = new ArrayList<>();

		Entry(Element element) {
			this.element = element;
		}

		public void displayContent(List<String> content) {
			if (content == null) {
				displayContent(Collections.emptyList());
				return;
			}

			resizeTo(content.size());

			List<Row> rows = this.rows;
			for (int x = 0, l = content.size(); x < l; x++) { // TODO "parallel iterator"
				rows.get(x).display(content.get(x));
			}
		}

		private void resizeTo(int size) {
			int currentSize = rows.size();

			if (currentSize == size) {
				return;
			}

			if (currentSize > size) {
				ListHelper.cutToSize(rows, size, Row::unregister);
			} else {
				ListHelper.growToSize(rows, size, Row::new);
			}
		}

		public void unregister() {
			rows.forEach(Row::unregister);
		}

		@Override
		public int compareTo(Entry entry) {
			return element.compareTo(entry.element);
		}
	}

	private final class Row {
		private final Team team;
		private final Score score;

		Row() {
			String entry = ScoreboardHelper.randomInvisibleEntry(); // TODO validate uniqueness
			this.team = scoreboard.registerNewTeam(entry);
			this.team.addEntry(entry);
			this.score = objective.getScore(entry);
		}

		public void setScore(int score) {
			this.score.setScore(score);
		}

		public void unregister() {
			team.unregister();
			scoreboard.resetScores(score.getEntry()); // TODO is this correct?
		}

		public void display(String text) {
			if (isBig(text)) {
				displayBig(text);
				return;
			}

			displaySmall(text);
		}

		private boolean isBig(String text) {
			return text.length() > 16;
		}

		private void displayBig(String text) { // TODO display huge?
			String prefix = text.substring(0, 16);
			String suffix = text.substring(16, Integer.max(16, text.length()));
			team.setPrefix(prefix);
			team.setSuffix(suffix);
		}

		private void displaySmall(String text) {
			team.setPrefix(text);

			if (team.getSuffix() != null) {
				team.setSuffix("");
			}
		}
	}

}