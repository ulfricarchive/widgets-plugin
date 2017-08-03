package com.ulfric.monty;

import org.bukkit.entity.Player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.ulfric.commons.time.TemporalHelper;
import com.ulfric.tryto.Try;

import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class TimerElement extends Element {

	private final Cache<Player, Text> cache; // TODO is guava the fastest option? Caffeine?

	public TimerElement(TemporalAmount frequency) {
		this(Priority.NORMAL, frequency);
	}

	public TimerElement(Priority priority, TemporalAmount frequency) {
		super(priority);

		Objects.requireNonNull(frequency, "frequency");

		long frequencyMillis = TemporalHelper.millisFrom(frequency);
		this.cache = CacheBuilder.newBuilder()
				.concurrencyLevel(1)
				.weakKeys()
				.expireAfterWrite(frequencyMillis, TimeUnit.MILLISECONDS)
				.build();
	}

	@Override
	public final Text apply(Player player) {
		return Try.toGet(() -> cache.get(player, () -> readTimer(player)));
	}

	public abstract Text readTimer(Player player);

}