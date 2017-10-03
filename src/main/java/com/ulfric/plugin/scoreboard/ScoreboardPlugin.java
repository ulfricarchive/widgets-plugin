package com.ulfric.plugin.scoreboard;

import com.ulfric.plugin.Plugin;

public class ScoreboardPlugin extends Plugin {

	public ScoreboardPlugin() {
		install(ScoreboardTickLoopContainer.class);
	}

}
