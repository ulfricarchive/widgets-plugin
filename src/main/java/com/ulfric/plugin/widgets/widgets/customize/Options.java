package com.ulfric.plugin.widgets.widgets.customize;

import com.ulfric.commons.value.Bean;
import com.ulfric.plugin.widgets.scoreboard.text.ScoreboardStyle;
import com.ulfric.plugin.widgets.tab.text.TabStyle;

public class Options extends Bean {

	private ScoreboardStyle scoreboardStyle; // TODO support Style interface
	private TabStyle tabStyle;

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

}
