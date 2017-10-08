package com.ulfric.plugin.scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

	@EventHandler
	public void on(PlayerJoinEvent event) {
		Scoreboard.create(event.getPlayer());
	}

	@EventHandler
	public void on(PlayerQuitEvent event) {
		Scoreboard.clearScoreboard(event.getPlayer());
	}

}