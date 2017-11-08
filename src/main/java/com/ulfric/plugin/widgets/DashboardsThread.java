package com.ulfric.plugin.widgets;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

final class DashboardsThread extends Thread { // TODO should this run in the bukkit scheduler?

	public DashboardsThread() {
		super("dashboards-tick-loop");
	}

	@Override
	public void run() {
		Collection<List<Dashboard>> dashboards = Dashboard.getAllDashboardsMutableView();
		while (running()) {
			dashboards.forEach(this::updateAll);
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

	private void updateAll(List<Dashboard> dashboards) {
		dashboards.forEach(this::update);
	}

	private void update(Dashboard dashboard) {
		dashboard.queueUpdate(TimerWidget.class);
		dashboard.update();
	}

}