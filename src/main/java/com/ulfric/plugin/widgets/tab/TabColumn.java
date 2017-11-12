package com.ulfric.plugin.widgets.tab;

import java.util.Arrays;
import java.util.List;

import com.ulfric.plugin.widgets.tab.Tab.TabRow;
import com.ulfric.plugin.widgets.text.graph.Row;

public class TabColumn {
	// TODO use max player count to control numbers
	// TODO cleanup
	// TODO optimize

	private final Tab tab;
	private final List<TabRow> rows = Arrays.asList(new TabRow[20]);
	private int nextAvailableRow;

	TabColumn(Tab tab) {
		this.tab = tab;
	}

	void generate(int index) {
		rows.set(index, tab.createRow(this, index));
	}

	public int getSpace() {
		return 20 - nextAvailableRow;
	}

	public Row getNextRow() {
		if (nextAvailableRow == 20) {
			return null;
		}

		Row row = rows.get(nextAvailableRow);
		nextAvailableRow++;
		return row;
	}

	public void release(int index) {
		if (nextAvailableRow <= index) {
			return;
		}

		rows.get(index).clear();

		for (int x = index + 1; x < nextAvailableRow; x++) {
			Row insert = rows.get(index - 1);

			if (x == 20) {
				insert.clear();
				continue;
			}

			Row current = rows.get(index);
			insert.display(current.getText());
		}

		nextAvailableRow--;
	}

	public Row acquire(int index) {
		if (index >= rows.size()) {
			return null;
		}

		if (index == nextAvailableRow) {
			return getNextRow();
		}

		if (index > nextAvailableRow) {
			Row row = null;
			for (int x = nextAvailableRow; x < index; x++) {
				row = getNextRow();
			}
			return row;
		}

		String currentText = null;
		for (int x = index; x < nextAvailableRow; x++) {
			Row current = rows.get(x);
			current.display(currentText);

			int nextIndex = x + 1;
			if (nextIndex == 20) {
				continue;
			}

			Row next = rows.get(nextIndex);
			currentText = next.getText();
		}

		return rows.get(index);
	}

}
