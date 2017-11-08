package com.ulfric.plugin.widgets.scoreboard;

import com.ulfric.plugin.widgets.Dashboard;
import com.ulfric.plugin.widgets.Dashboards;

public abstract class ScoreboardDashboard extends Dashboard {

	protected final org.bukkit.scoreboard.Scoreboard scoreboard;

	public ScoreboardDashboard(Dashboards owner) {
		super(owner);

		this.scoreboard = owner.getViewer().getScoreboard();
	}

}
