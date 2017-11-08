package com.ulfric.plugin.widgets;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.ulfric.dragoon.extension.inject.Inject;

public class BukkitScoreboardInitializeListener implements Listener {

	@Inject
	private ScoreboardManager manager;

	@EventHandler
	public void on(PlayerJoinEvent event) {
		Scoreboard scoreboard = manager.getNewScoreboard();
		event.getPlayer().setScoreboard(scoreboard);
	}

}
