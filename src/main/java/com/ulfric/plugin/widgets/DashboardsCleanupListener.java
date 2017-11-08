package com.ulfric.plugin.widgets;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DashboardsCleanupListener implements Listener {

	@EventHandler
	public void on(PlayerQuitEvent event) {
		Dashboards.clearDashboards(event.getPlayer());
	}

}