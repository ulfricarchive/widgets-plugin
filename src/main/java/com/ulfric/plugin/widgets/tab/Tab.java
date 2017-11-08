package com.ulfric.plugin.widgets.tab;

import java.util.Objects;

import org.bukkit.entity.Player;

import com.ulfric.plugin.widgets.Dashboards;
import com.ulfric.plugin.widgets.Widget;
import com.ulfric.plugin.widgets.scoreboard.ScoreboardDashboard;
import com.ulfric.plugin.widgets.scoreboard.ScoreboardHelper;
import com.ulfric.plugin.widgets.tab.text.TabStyle;
import com.ulfric.plugin.widgets.text.Style;
import com.ulfric.plugin.widgets.text.graph.Entry;

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

		Player viewer = owner.getViewer();
		for (int x = 0; x < TOTAL_TAB_ENTRIES; x++) {
			viewer.addPlayerListEntry(ScoreboardHelper.randomInvisibleEntry()); // TODO could cache these to avoid random generation 60x for every new tab
		}
	}

	@Override
	protected Entry createEntry(Widget widget) {
		return null;
	}

}
