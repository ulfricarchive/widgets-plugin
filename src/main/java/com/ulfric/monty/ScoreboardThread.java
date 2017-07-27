package com.ulfric.monty;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

final class ScoreboardThread extends Thread { // TODO should this run in the bukkit scheduler?

	public ScoreboardThread() {
		super("monty-scoreboard");
	}

	@Override
	public void run() {
		Collection<Scoreboard> scoreboards = Scoreboard.getAllScoreboardsMutableView();
		while (running()) {
			scoreboards.forEach(Scoreboard::update);
		}
	}

	private boolean running() {
		try {
			TimeUnit.SECONDS.sleep(1);
			return true;
		} catch (InterruptedException thatsOk) {
			// TODO log shutdown?
			return false;
		}
	}

}