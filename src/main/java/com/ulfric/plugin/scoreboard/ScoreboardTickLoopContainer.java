package com.ulfric.plugin.scoreboard;

import com.ulfric.dragoon.application.Container;
import com.ulfric.plugin.scoreboard.customize.OptionsService;

public class ScoreboardTickLoopContainer extends Container {

	private Thread scoreboard;

	public ScoreboardTickLoopContainer() {
		install(OptionsService.class);
		install(ScoreboardListener.class);

		addBootHook(this::startScoreboardThread);
		addShutdownHook(this::stopScoreboardThread);
	}

	private void startScoreboardThread() {
		scoreboard = new ScoreboardThread();
		scoreboard.setContextClassLoader(getClass().getClassLoader());
		scoreboard.start();
	}

	private void stopScoreboardThread() {
		scoreboard.interrupt();
		scoreboard = null;
	}

}