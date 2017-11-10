package com.ulfric.plugin.widgets.text.graph;

public interface Row {

	void setPosition(int position);

	void unregister();

	void clear();

	void display(String text);

	String getText();

}
