package com.ulfric.plugin.scoreboard.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum StandardStyles implements Style { // TODO text wrapping

	FLAT { // TODO duplicate code with HEADER
		@Override
		public List<String> apply(Text entry) {
			List<String> styled = new ArrayList<>();
			styled.add(BLANK_LINE);

			if (entry.getTitle() != null) {
				styled.add(entry.getTitle());
			}

			if (entry.getBody() != null) {
				styled.addAll(entry.getBody());
			}

			return styled.size() == 1 ? Collections.emptyList() : styled;
		}
	},

	HEADER { // TODO duplicate code with FLAT
		@Override
		public List<String> apply(Text entry) {
			List<String> styled = new ArrayList<>();
			styled.add(BLANK_LINE);

			if (entry.getTitle() != null) {
				styled.add(entry.getTitle());
			}

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
	}; // TODO "TREE" style

	public static StandardStyles defaultStyle() {
		return FLAT;
	}

	private static final String BLANK_LINE = "";

	@Override
	public final String getName() {
		return name();
	}

}