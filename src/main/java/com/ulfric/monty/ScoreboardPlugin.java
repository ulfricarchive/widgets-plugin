package com.ulfric.monty;

import com.ulfric.plugin.Plugin;

public class ScoreboardPlugin extends Plugin {

	public ScoreboardPlugin() {
		install(ScoreboardContainer.class);
	}

}
