package com.ulfric.plugin.widgets;

import com.ulfric.plugin.Plugin;

public class WidgetsPlugin extends Plugin {

	public WidgetsPlugin() {
		install(DashboardsTickLoopContainer.class);
		install(DashboardsCleanupListener.class);
		install(BukkitScoreboardInitializeListener.class);
	}

}
