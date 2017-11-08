package com.ulfric.plugin.widgets.widgets.customize;

import java.util.Map;

import com.ulfric.commons.value.Bean;
import com.ulfric.plugin.widgets.StandardDashboardType;
import com.ulfric.plugin.widgets.Widget;
import com.ulfric.plugin.widgets.scoreboard.text.ScoreboardStyle;
import com.ulfric.plugin.widgets.tab.text.TabStyle;

public class Options extends Bean {

	private ScoreboardStyle scoreboardStyle; // TODO support Style interface
	private TabStyle tabStyle;
	private Map<Class<? extends Widget>, StandardDashboardType> widgets;

	public ScoreboardStyle getScoreboardStyle() {
		return scoreboardStyle;
	}

	public void setScoreboardStyle(ScoreboardStyle scoreboardStyle) {
		this.scoreboardStyle = scoreboardStyle;
	}

	public TabStyle getTabStyle() {
		return tabStyle;
	}

	public void setTabStyle(TabStyle tabStyle) {
		this.tabStyle = tabStyle;
	}

	public Map<Class<? extends Widget>, StandardDashboardType> getWidgets() {
		return widgets;
	}

	public void setWidgets(Map<Class<? extends Widget>, StandardDashboardType> widgets) {
		this.widgets = widgets;
	}

}
