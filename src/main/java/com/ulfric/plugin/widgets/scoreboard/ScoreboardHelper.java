package com.ulfric.plugin.widgets.scoreboard;

import java.util.Random;

import org.bukkit.ChatColor;

public class ScoreboardHelper {

	private static final Random RANDOM = new Random();
	private static final ChatColor[] COLORS = ChatColor.values();

	public static String randomInvisibleEntry() {
		int length = COLORS.length;
		StringBuilder builder = new StringBuilder(16); // TODO detail 16 and 8 values
		for (int x = 0; x < 8; x++) {
			builder.append(COLORS[RANDOM.nextInt(length)]);
		}
		return builder.toString();
	}

	private ScoreboardHelper() {
	}

}