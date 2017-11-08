package com.ulfric.plugin.widgets;

import org.bukkit.entity.Player;

import com.ulfric.plugin.widgets.text.Text;

import java.util.Objects;
import java.util.function.Function;

public abstract class Widget implements Function<Player, Text>, Comparable<Widget> {

	private final Priority priority;

	public Widget() {
		this(Priority.NORMAL);
	}

	public Widget(Priority priority) {
		Objects.requireNonNull(priority, "priority");

		this.priority = priority;
	}

	@Override
	public final int compareTo(Widget other) {
		return priority.compareTo(other.priority);
	}

}