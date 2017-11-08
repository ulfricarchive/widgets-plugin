package com.ulfric.plugin.widgets.text;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.ulfric.plugin.widgets.scoreboard.text.ScoreboardStyle;

import java.util.Map;
import java.util.Objects;

public class Styles {

	private static final Map<String, Style> STYLES = new CaseInsensitiveMap<>();

	static {
		for (Style style : ScoreboardStyle.values()) {
			register(style);
		}
	}

	public static Style getStyle(String name) {
		return STYLES.get(name);
	}

	public static void register(Style style) {
		Objects.requireNonNull(style, "style");

		String name = style.getName();
		Objects.requireNonNull(style.getName(), "name");

		STYLES.put(name, style);
	}

	public static void unregister(Style style) {
		Objects.requireNonNull(style, "style");

		STYLES.remove(style.getName(), style);
	}

	private Styles() {
	}

}