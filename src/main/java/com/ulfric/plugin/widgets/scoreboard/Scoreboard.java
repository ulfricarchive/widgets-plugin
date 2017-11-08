package com.ulfric.plugin.widgets.scoreboard;

import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import com.ulfric.fancymessage.Message;
import com.ulfric.plugin.locale.LocaleService;
import com.ulfric.plugin.widgets.Dashboard;
import com.ulfric.plugin.widgets.Dashboards;
import com.ulfric.plugin.widgets.Widget;
import com.ulfric.plugin.widgets.scoreboard.text.ScoreboardStyle;
import com.ulfric.plugin.widgets.text.Style;
import com.ulfric.plugin.widgets.text.graph.Entry;
import com.ulfric.plugin.widgets.text.graph.Row;

public final class Scoreboard extends Dashboard {

	private static final String DUMMY_CRITERIA = "dummy";

	public static Scoreboard getScoreboard(Dashboards dashboards) {
		// TODO lock the dashboards to prevent adding duplicate scoreboards.
		// Concurrency issue that should never really happen anyways. Low priority.
		Objects.requireNonNull(dashboards, "dashboards");

		Scoreboard scoreboard = dashboards.getDashboardByType(Scoreboard.class);
		if (scoreboard != null) {
			return scoreboard;
		}

		org.bukkit.scoreboard.Scoreboard display = ScoreboardHelper.getNewBukkitScoreboard();
		String title = Message.toLegacy(LocaleService.getMessage(dashboards.getViewer(), "scoreboard-title"));

		scoreboard = new Scoreboard(dashboards, display, title);
		dashboards.getViewer().setScoreboard(display);
		return scoreboard;
	}

	private final org.bukkit.scoreboard.Scoreboard scoreboard;
	private final Objective objective;

	private Scoreboard(Dashboards owner, org.bukkit.scoreboard.Scoreboard scoreboard, String title) {
		super(owner);

		this.scoreboard = scoreboard;

		this.objective = scoreboard.registerNewObjective(title, DUMMY_CRITERIA);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	@Override
	protected Entry createEntry(Widget widget) {
		return new ScoreboardEntry(widget);
	}

	@Override
	protected Style getStyle() {
		Style style = owner.getOptions().getScoreboardStyle();
		return style == null ? ScoreboardStyle.defaultStyle() : style;
	}

	@Override
	protected int getMaxLineLength() {
		return 32;
	}

	@Override
	public boolean update() {
		if (!super.update()) {
			return false;
		}

		updateScores();
		return true;
	}

	private void updateScores() {
		int score = 0;
		Iterator<Entry> entries = new ReverseListIterator<>(this.entries);
		while (entries.hasNext()) {
			Entry entry = entries.next();

			Iterator<Row> rows = new ReverseListIterator<>(entry.getRows());
			while (rows.hasNext()) {
				Row row = rows.next();
				row.setPosition(score++);
			}
		}
	}

	private final class ScoreboardEntry extends Entry {
		ScoreboardEntry(Widget widget) {
			super(widget);
		}

		@Override
		protected Row createRow() {
			return new ScoreboardRow();
		}
	}

	private final class ScoreboardRow extends Row {
		private final Team team;
		private final Score score;

		ScoreboardRow() {
			String entry = ScoreboardHelper.randomInvisibleEntry(); // TODO validate uniqueness
			this.team = scoreboard.registerNewTeam(entry);
			this.team.addEntry(entry);
			this.score = objective.getScore(entry);
		}

		@Override
		public void setPosition(int score) {
			this.score.setScore(score);
		}

		@Override
		public void unregister() {
			team.unregister();
			scoreboard.resetScores(score.getEntry()); // TODO is this correct?
		}

		@Override
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

		private void displayBig(String text) {
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