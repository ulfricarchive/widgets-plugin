package com.ulfric.plugin.widgets;

import java.util.Objects;
import java.util.function.Function;

import org.bukkit.entity.Player;

import com.ulfric.plugin.widgets.text.Text;

public abstract class Widget implements Function<Player, Text>, Comparable<Widget> {

	private final DashboardType defaultDashboard;
	private final Priority priority;

	public Widget() {
		this(StandardDashboardType.NONE);
	}

	public Widget(DashboardType defaultDashboard) {
		this(defaultDashboard, Priority.NORMAL);
	}

	public Widget(DashboardType defaultDashboard, Priority priority) {
		Objects.requireNonNull(priority, "priority");

		this.defaultDashboard = defaultDashboard == null ? StandardDashboardType.NONE : defaultDashboard;
		this.priority = priority;
	}

	@Override
	public final int compareTo(Widget other) {
		return priority.compareTo(other.priority);
	}

	public final DashboardType getDefaultDashboard() {
		return defaultDashboard;
	}

}