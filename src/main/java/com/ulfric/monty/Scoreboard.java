package com.ulfric.monty;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import org.apache.commons.collections4.SetUtils;

import com.ulfric.commons.collection.ListHelper;
import com.ulfric.commons.collection.MapHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public final class Scoreboard {

	private static final String DUMMY_CRITERIA = "dummy";
	private static final ConcurrentMap<UUID, Scoreboard> SCOREBOARS_BY_PLAYER = MapHelper.newConcurrentMap(2);

	static Collection<Scoreboard> getAllScoreboardsMutableView() {
		return SCOREBOARS_BY_PLAYER.values();
	}

	static void setScoreboard(Player player, Scoreboard scoreboard) { // TODO fix race condition
		player.setScoreboard(scoreboard.scoreboard);
		SCOREBOARS_BY_PLAYER.put(player.getUniqueId(), scoreboard);
	}

	static void clearScoreboard(Player player) { // TODO fix race condition
		Scoreboard scoreboard = SCOREBOARS_BY_PLAYER.remove(player.getUniqueId());
		if (scoreboard != null) {
			if (scoreboard.players.remove(player)) {
				player.setScoreboard(ScoreboardHelper.getBukkitGlobalScoreboard());
			}
		}
	}

	public static Scoreboard getScoreboard(Player player) {
		return SCOREBOARS_BY_PLAYER.get(player.getUniqueId());
	}

	private final org.bukkit.scoreboard.Scoreboard scoreboard;
	private final Objective objective;
	private final List<Entry> entries = new ArrayList<>();
	private final List<Player> players = new ArrayList<>();
	private final Style style = StandardStyles.FLAT; // TODO implement style changing

	private final Set<Class<? extends Element>> update = SetUtils.newIdentityHashSet();

	Scoreboard(ScoreboardManager manager) {
		Objects.requireNonNull(manager, "manager");

		this.scoreboard = manager.getNewScoreboard();
		this.objective = scoreboard.registerNewObjective("TODO -- title", DUMMY_CRITERIA); // TODO
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public void update() { // TODO element removal
		Player player = players.isEmpty() ? null : players.get(0);

		int changed = 0;
		for (Class<? extends Element> update : this.update) {
			for (Entry entry : entries) {
				if (changed != 0) {
					entry.bump(changed); // TODO
				}

				Element elementOnBoard = entry.element;

				if (update.isInstance(elementOnBoard)) {
					List<RawEntry> rawEntries = entry.entries;
					Text text = elementOnBoard.apply(player);
					List<String> setOnBoard = style.apply(text);
					int size = setOnBoard.size();
					int difference = rawEntries.size() - size;
					changed += difference;
					entry.resize(size);

					for (int x = 0; x < size; x++) {
						rawEntries.get(x).display(setOnBoard.get(x));
					}
				}
			}
		}
	}

	public void queueUpdate(Class<? extends Element> update) {
		this.update.add(update);
	}

	public void add(Element element) {
		entries.add(new Entry(element));
	}

	final class Entry {

		final List<RawEntry> entries = new ArrayList<>();  // TODO trove int map
		final Element element;

		Entry(Element element) {
			this.element = element;
		}

		void bump(int amount) {
			entries.forEach(entry -> entry.score.setScore(entry.score.getScore() + amount));
		}

		void resize(int size) {
			int currentSize = entries.size();

			if (currentSize == size) {
				return;
			}

			if (currentSize > size) {
				ListHelper.cutToSize(entries, size, RawEntry::delete);
			} else {
				ListHelper.growToSize(entries, size, ignore -> newEntry());
			}
		}

		RawEntry newEntry() {
			int row = entries.isEmpty() ? 0 : (entries.get(entries.size() - 1).score.getScore() + 1); // TODO optimize for resize?

			String entry = ScoreboardHelper.randomInvisibleEntry();

			Team team = scoreboard.registerNewTeam(Integer.toString(row));
			team.addEntry(entry);

			Score score = objective.getScore(entry);
			score.setScore(row);
			return new RawEntry(team, score);
		}
	}

	final class RawEntry {
		final Team team;
		final Score score;

		RawEntry(Team team, Score score) {
			this.team = team;
			this.score = score;
		}

		void display(String text) {
			if (isBig(text)) {
				displayBig(text);
				return;
			}

			displaySmall(text);
		}

		void delete() {
			team.unregister();
			scoreboard.resetScores(score.getEntry()); // TODO is this correct?
		}

		private boolean isBig(String text) {
			return text.length() > 16;
		}

		private void displayBig(String text) {
			String prefix = text.substring(0, 16);
			String suffix = text.substring(16, Integer.max(16, text.length()));
			team.setPrefix(prefix);
			team.setSuffix(suffix);
		}

		private void displaySmall(String text) {
			team.setPrefix(text);

			if (team.getSuffix() != null) {
				team.setSuffix(null);
			}
		}
	}

}
