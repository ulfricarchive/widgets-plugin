package com.ulfric.monty;

import com.ulfric.monty.customize.OptionsService;
import com.ulfric.platform.Plugin;

public class Monty extends Plugin {

	private Thread scoreboard;

	public Monty() {
		install(OptionsService.class);
		install(ScoreboardListener.class);

		addBootHook(this::startScoreboardThread);
		addShutdownHook(this::stopScoreboardThread);
	}

	private void startScoreboardThread() {
		scoreboard = new ScoreboardThread();
		scoreboard.start();
	}

	private void stopScoreboardThread() {
		scoreboard.interrupt();
		scoreboard = null;
	}

}