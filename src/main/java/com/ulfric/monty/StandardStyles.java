package com.ulfric.monty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum StandardStyles implements Style { // TODO text wrapping

	FLAT {
		@Override
		public List<String> apply(Text entry) {
			List<String> styled = new ArrayList<>();

			if (entry.getTitle() != null) {
				styled.add(entry.getTitle());
			}

			if (entry.getBody() != null) {
				styled.addAll(entry.getBody());
			}

			return styled;
		}
	},

	HEADER {
		@Override
		public List<String> apply(Text entry) {
			List<String> styled = new ArrayList<>();

			if (entry.getTitle() != null) {
				styled.add(entry.getTitle());
			}

			if (entry.getBody() != null) {
				entry.getBody().stream()
					.map(body -> " " + body)
					.forEach(styled::add);
			}

			return styled;
		}
	},

	MECHANICAL {
		@Override
		public List<String> apply(Text entry) {
			return entry.getBody() == null ? Collections.emptyList() : entry.getBody();
		}
	};

	@Override
	public final String getName() {
		return name();
	}

}