package com.ulfric.plugin.widgets;

import org.bukkit.entity.Player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.ulfric.commons.time.TemporalHelper;
import com.ulfric.plugin.widgets.text.Text;
import com.ulfric.tryto.TryTo;

import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class TimerWidget extends Widget {

	private final Cache<Player, Text> cache;

	public TimerWidget(TemporalAmount frequency) {
		this(Priority.NORMAL, frequency);
	}

	public TimerWidget(Priority priority, TemporalAmount frequency) {
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
		return TryTo.get(() -> cache.get(player, () -> readTimer(player)));
	}

	public abstract Text readTimer(Player player);

}