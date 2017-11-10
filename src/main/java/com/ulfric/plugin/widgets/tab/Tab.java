package com.ulfric.plugin.widgets.tab;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.ulfric.plugin.widgets.Dashboards;
import com.ulfric.plugin.widgets.Widget;
import com.ulfric.plugin.widgets.scoreboard.ScoreboardDashboard;
import com.ulfric.plugin.widgets.scoreboard.ScoreboardHelper;
import com.ulfric.plugin.widgets.tab.text.TabStyle;
import com.ulfric.plugin.widgets.text.Style;
import com.ulfric.plugin.widgets.text.graph.Entry;
import com.ulfric.plugin.widgets.text.graph.Row;

public class Tab extends ScoreboardDashboard {

	private static final int TOTAL_TAB_ENTRIES = 60;

	public static Tab getTab(Dashboards dashboards) {
		// TODO lock the dashboards to prevent adding duplicate tabs.
		// Concurrency issue that should never really happen anyways. Low priority.
		Objects.requireNonNull(dashboards, "dashboards");

		Tab tab = dashboards.getDashboardByType(Tab.class);
		if (tab != null) {
			return tab;
		}

		return new Tab(dashboards);
	}

	private final TabColumns columns = new TabColumns(this);

	public Tab(Dashboards owner) {
		super(owner);
	}

	@Override
	protected Style getStyle() {
		Style style = owner.getOptions().getTabStyle();
		return style == null ? TabStyle.defaultStyle() : style;
	}

	@Override
	protected int getMaxLineLength() {
		return 16;
	}

	@Override
	protected void register() {
		super.register();

		createEmptyRows();
	}

	private void createEmptyRows() {
		Player viewer = owner.getViewer();
		for (int x = 0; x < TOTAL_TAB_ENTRIES; x++) {
			viewer.addPlayerListEntry(ScoreboardHelper.randomInvisibleEntry()); // TODO cache these to avoid random generation 60x for every new tab
		}
	}

	@Override
	protected Entry createEntry(Widget widget) {
		return new TabEntry(widget);
	}

	TabRow createRow(TabColumn column, int index) {
		return new TabRow(column, index);
	}

	private class TabEntry extends Entry {
		private final TabColumn column; // TODO make this dynamically change based on the rest of the tab menu

		TabEntry(Widget widget) {
			super(widget);

			this.column = columns.getFreeSpace();
		}

		@Override
		protected Row createRow() {
			if (column == null) {
				return null;
			}

			return column.getNextRow();
		}
	}

	final class TabRow implements Row {
		private final TabColumn column;
		private final int index;
		private final Team team = ScoreboardHelper.createRandomTeam(scoreboard);

		TabRow(TabColumn column, int index) {
			this.column = column;
			this.index = index;
		}

		@Override
		public void setPosition(int position) {
			if (position == index) {
				return;
			}

			String text = getText();
			Row newSpot = column.acquire(position);
			newSpot.display(text);
		}

		@Override
		public void unregister() {
			this.column.release(index);
		}

		@Override
		public void display(String text) {
			if (text == null) {
				clear();
				return;
			}

			int maxLength = getMaxLineLength();
			if (text.length() > maxLength) {
				text = text.substring(0, maxLength);
			}

			team.setPrefix(text);
		}

		@Override
		public void clear() {
			team.setPrefix("");
		}

		@Override
		public String getText() {
			String text = team.getPrefix();
			return text == null ? "" : text;
		}
	}

}
