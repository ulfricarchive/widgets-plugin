package com.ulfric.monty.element;

import org.bukkit.entity.Player;

import com.ulfric.monty.text.Text;

import java.util.Objects;
import java.util.function.Function;

public abstract class Element implements Function<Player, Text>, Comparable<Element> {

	private final Priority priority;

	public Element() {
		this(Priority.NORMAL);
	}

	public Element(Priority priority) {
		Objects.requireNonNull(priority, "priority");

		this.priority = priority;
	}

	@Override
	public final int compareTo(Element other) {
		return priority.compareTo(other.priority);
	}

}