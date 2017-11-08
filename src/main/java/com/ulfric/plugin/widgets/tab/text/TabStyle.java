package com.ulfric.plugin.widgets.tab.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.ulfric.plugin.widgets.text.Style;
import com.ulfric.plugin.widgets.text.Text;
import com.ulfric.plugin.widgets.text.TextHelper;

public enum TabStyle implements Style {

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

			if (styled.size() == 1) {
				return Collections.emptyList();
			}

			styled.add(TextHelper.BLANK_LINE);

			return styled;
		}
	},

	MECHANICAL {
		@Override
		public List<String> apply(Text entry) {
			List<String> styled = entry.getBody();

			if (CollectionUtils.isEmpty(styled)) {
				return Collections.emptyList();
			}

			styled.add(TextHelper.BLANK_LINE);

			return styled;
		}
	}; // TODO "TREE" style

	public static TabStyle defaultStyle() {
		return FLAT;
	}

	@Override
	public final String getName() {
		return name();
	}

}