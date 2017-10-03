package com.ulfric.plugin.scoreboard;

import com.ulfric.plugin.scoreboard.element.TimerElement;

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
			scoreboards.forEach(this::update);
		}
	}

	private boolean running() {
		try {
			TimeUnit.MILLISECONDS.sleep(100); // TODO more often
			return true;
		} catch (InterruptedException thatsOk) {
			// TODO log shutdown?
			return false;
		}
	}

	private void update(Scoreboard scoreboard) {
		scoreboard.queueUpdate(TimerElement.class);
		scoreboard.update();
	}

}