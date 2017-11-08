package com.ulfric.plugin.widgets;

import com.ulfric.plugin.Plugin;
import com.ulfric.plugin.widgets.scoreboard.ScoreboardContainer;

public class WidgetsPlugin extends Plugin {

	public WidgetsPlugin() {
		install(ScoreboardContainer.class);
	}

}
