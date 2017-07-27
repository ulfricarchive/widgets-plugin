package com.ulfric.monty;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

	@EventHandler
	public void on(PlayerLoginEvent event) {
		if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) { // TODO static helper?
			return;
		}

		// TODO ensure player has scoreboard enabled, load scoreboard settings
		Scoreboard.setScoreboard(event.getPlayer(), ScoreboardHelper.newScoreboard());
	}

	@EventHandler
	public void on(PlayerQuitEvent event) {
		Scoreboard.clearScoreboard(event.getPlayer());
	}

}