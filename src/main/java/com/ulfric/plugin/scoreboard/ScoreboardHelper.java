package com.ulfric.plugin.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Random;

public class ScoreboardHelper {

	private static final Random RANDOM = new Random();
	private static final ChatColor[] COLORS = ChatColor.values();

	public static org.bukkit.scoreboard.Scoreboard getBukkitGlobalScoreboard() {
		return defaultManager().getMainScoreboard();
	}

	public static org.bukkit.scoreboard.Scoreboard getNewBukkitScoreboard() {
		return defaultManager().getNewScoreboard();
	}

	public static ScoreboardManager defaultManager() {
		return Bukkit.getScoreboardManager();
	}

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