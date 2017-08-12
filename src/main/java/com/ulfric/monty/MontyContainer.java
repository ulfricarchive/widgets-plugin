package com.ulfric.monty;

import com.ulfric.dragoon.application.Container;
import com.ulfric.monty.customize.OptionsService;

public class MontyContainer extends Container {

	private Thread scoreboard;

	public MontyContainer() {
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