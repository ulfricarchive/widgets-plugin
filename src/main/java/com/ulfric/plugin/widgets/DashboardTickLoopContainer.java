package com.ulfric.plugin.widgets;

import com.ulfric.dragoon.application.Container;
import com.ulfric.plugin.widgets.widgets.customize.OptionsService;

public class DashboardTickLoopContainer extends Container {

	private Thread dashboard;

	public DashboardTickLoopContainer() {
		install(OptionsService.class);

		addBootHook(this::startDashboardsThread);
		addShutdownHook(this::stopDashboardsThread);
	}

	private void startDashboardsThread() {
		dashboard = new DashboardsThread();
		dashboard.setContextClassLoader(getClass().getClassLoader());
		dashboard.start();
	}

	private void stopDashboardsThread() {
		dashboard.interrupt();
		dashboard = null;
	}

}