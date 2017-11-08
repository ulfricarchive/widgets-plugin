package com.ulfric.plugin.widgets.text.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.ulfric.commons.collection.ListHelper;
import com.ulfric.plugin.widgets.Widget;

public abstract class Entry implements Comparable<Entry> {

	private final Widget widget;
	private final List<Row> rows = new ArrayList<>();

	public Entry(Widget widget) {
		Objects.requireNonNull(widget, "widget");

		this.widget = widget;
	}

	public void displayContent(List<String> content) {
		if (content == null) {
			displayContent(Collections.emptyList());
			return;
		}

		resizeTo(content.size());

		List<Row> rows = this.rows;
		for (int x = 0, l = content.size(); x < l; x++) { // TODO "parallel iterator"
			rows.get(x).display(content.get(x));
		}
	}

	private void resizeTo(int size) {
		int currentSize = rows.size();

		if (currentSize == size) {
			return;
		}

		if (currentSize > size) {
			ListHelper.cutToSize(rows, size, Row::unregister);
		} else {
			ListHelper.growToSize(rows, size, this::createRow);
		}
	}

	public void unregister() {
		rows.forEach(Row::unregister);
	}

	@Override
	public int compareTo(Entry entry) {
		return widget.compareTo(entry.widget);
	}

	public Widget getWidget() {
		return widget;
	}

	public List<Row> getRows() {
		return Collections.unmodifiableList(rows); // TODO cache unmodList
	}

	protected abstract Row createRow();

}
