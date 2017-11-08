package com.ulfric.plugin.widgets.scoreboard.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ulfric.plugin.widgets.text.Style;
import com.ulfric.plugin.widgets.text.Text;
import com.ulfric.plugin.widgets.text.TextHelper;

public enum ScoreboardStyle implements Style {

	FLAT { // TODO duplicate code with HEADER
		@Override
		public List<String> apply(Text entry) {
			List<String> styled = createStyledWithTitle(entry);

			if (entry.getBody() != null) {
				styled.addAll(entry.getBody());
			}

			return styled.size() == 1 ? Collections.emptyList() : styled;
		}
	},

	HEADER { // TODO duplicate code with FLAT
		@Override
		public List<String> apply(Text entry) {
			List<String> styled = createStyledWithTitle(entry);

			if (entry.getBody() != null) {
				entry.getBody().stream()
					.map(body -> " " + body) // TODO does two spaces look better?
					.forEach(styled::add);
			}

			return styled.size() == 1 ? Collections.emptyList() : styled;
		}
	},

	MECHANICAL {
		@Override
		public List<String> apply(Text entry) {
			return entry.getBody() == null ? Collections.emptyList() : entry.getBody();
		}
	};

	private static List<String> createStyledWithTitle(Text entry) {
		List<String> styled = new ArrayList<>();
		styled.add(TextHelper.BLANK_LINE);

		if (entry.getTitle() != null) {
			styled.add(entry.getTitle());
		}

		return styled;
	}

	public static ScoreboardStyle defaultStyle() {
		return FLAT;
	}

	@Override
	public final String getName() {
		return name();
	}

}