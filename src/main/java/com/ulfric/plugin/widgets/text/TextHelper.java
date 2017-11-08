package com.ulfric.plugin.widgets.text;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

public class TextHelper {

	public static final String BLANK_LINE = "";

	public static List<String> wrap(List<String> text, int maxLength) {
		List<String> wrapped = new ArrayList<>(text.size());
		Splitter splitter = Splitter.fixedLength(maxLength);

		for (String line : text) {
			int length = line.length();

			if (length <= maxLength) {
				wrapped.add(line);
				continue;
			}

			wrapped.addAll(splitter.splitToList(line));
		}

		return wrapped;
	}

	private TextHelper() {
	}

}
