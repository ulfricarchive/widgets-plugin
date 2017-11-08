package com.ulfric.plugin.widgets;

import com.ulfric.plugin.widgets.scoreboard.Scoreboard;
import com.ulfric.plugin.widgets.tab.Tab;

public enum StandardDashboardType implements DashboardType {

	NONE {
		@Override
		public Dashboard getDashboard(Dashboards dashboards) {
			return null;
		}
	},

	TAB {
		@Override
		public Dashboard getDashboard(Dashboards dashboards) {
			return Tab.getTab(dashboards);
		}
	},

	SCOREBOARD {
		@Override
		public Dashboard getDashboard(Dashboards dashboards) {
			return Scoreboard.getScoreboard(dashboards);
		}
	};

}
