package com.ulfric.plugin.widgets.tab;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class TabColumns { // TODO use max player count to control numbers

	private final List<TabColumn> columns;

	TabColumns(Tab tab) {
		this.columns = Stream.generate(() -> new TabColumn(tab))
				.limit(3)
				.collect(Collectors.toList()); 

		generate();
	}

	private void generate() {
		for (int x = 0; x < 20; x++) {
			for (TabColumn column : columns) {
				column.generate(x);
			}
		}
	}

	public TabColumn getFreeSpace() {
		TabColumn freeSpace = null;

		for (TabColumn column : columns) {
			int columnSpace = column.getSpace();
			if (columnSpace <= 0) {
				continue;
			}

			if (freeSpace == null) {
				freeSpace = column;
				continue;
			}

			if (freeSpace.getSpace() < columnSpace) {
				freeSpace = column;
			}
		}

		return freeSpace;
	}

}
