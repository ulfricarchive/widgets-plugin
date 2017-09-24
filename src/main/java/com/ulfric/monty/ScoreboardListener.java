package com.ulfric.monty;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.tasks.Scheduler;

public class ScoreboardListener implements Listener {

	@Inject
	private Scheduler scheduler;

	@EventHandler
	public void on(PlayerJoinEvent event) {
		Scoreboard.create(event.getPlayer());
	}

	@EventHandler
	public void on(PlayerQuitEvent event) {
		Scoreboard.clearScoreboard(event.getPlayer());
	}

}