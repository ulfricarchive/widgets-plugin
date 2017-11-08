package com.ulfric.plugin.widgets;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

final class DashboardsThread extends Thread { // TODO should this run in the bukkit scheduler?

	public DashboardsThread() {
		super("dashboards-tick-loop");
	}

	@Override
	public void run() {
		Collection<Dashboards> dashboards = Dashboards.getAllDashboardsMutableView();
		while (running()) {
			dashboards.forEach(this::update);
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

	private void update(Dashboards dashboard) {
		dashboard.update();
	}

}