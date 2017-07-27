package com.ulfric.monty;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Random;

public class ScoreboardHelper {

	public static void main(String[] args) {
		System.out.println(randomInvisibleEntry());
	}

	private static final Random RANDOM = new Random();
	private static final ChatColor[] COLORS = ChatColor.values();

	public static Scoreboard newScoreboard() {
		return new Scoreboard(Bukkit.getScoreboardManager());
	}

	public static org.bukkit.scoreboard.Scoreboard getBukkitGlobalScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}

	static String randomInvisibleEntry() {
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