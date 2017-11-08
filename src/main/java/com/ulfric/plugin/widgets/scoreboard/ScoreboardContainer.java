package com.ulfric.plugin.widgets.scoreboard;

import com.ulfric.dragoon.application.Container;
import com.ulfric.plugin.widgets.DashboardTickLoopContainer;

public class ScoreboardContainer extends Container {

	public ScoreboardContainer() {
		install(DashboardTickLoopContainer.class);
		install(ScoreboardListener.class);
	}

}
