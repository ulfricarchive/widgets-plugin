package com.ulfric.plugin.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.SetUtils;
import org.bukkit.entity.Player;

import com.ulfric.plugin.widgets.text.Style;
import com.ulfric.plugin.widgets.text.Text;
import com.ulfric.plugin.widgets.text.TextHelper;
import com.ulfric.plugin.widgets.text.graph.Entry;

public abstract class Dashboard {

	protected final Dashboards owner;
	protected final Set<Class<? extends Widget>> update = SetUtils.newIdentityHashSet();
	protected final Set<Widget> removed = SetUtils.newIdentityHashSet();
	protected final List<Entry> entries = new ArrayList<>();

	public Dashboard(Dashboards owner) {
		Objects.requireNonNull(owner, "owner");

		this.owner = owner;

		register();
	}

	protected void register() {
		owner.add(this);
	}

	public final void queueUpdate(Class<? extends Widget> widget) {
		Objects.requireNonNull(widget, "widget");

		update.add(widget);
	}

	protected final boolean needsUpdate() {
		return !update.isEmpty() || !removed.isEmpty();
	}

	public final void add(Widget widget) {
		Objects.requireNonNull(widget, "widget");

		entries.add(createEntry(widget));
		Collections.sort(entries);
		queueUpdate(widget.getClass());
	}

	public final void remove(Widget widget) {
		Objects.requireNonNull(widget, "widget");

		removed.add(widget);
	}

	public boolean update() {
		if (!needsUpdate()) {
			return false;
		}

		processRemovals();
		processUpdates();
		return true;
	}

	private void processUpdates() {
		Player viewer = owner.getViewer();
		Style style = getStyle();
		for (Entry entry : entries) {
			for (Class<? extends Widget> update : this.update) {
				Widget widget = entry.getWidget();
				if (!update.isInstance(widget)) {
					continue;
				}

				Text text = widget.apply(viewer);
				List<String> content = text == null ? Collections.emptyList() : style.apply(text);
				content = TextHelper.wrap(content, getMaxLineLength());
				entry.displayContent(content);

				break;
			}
		}
		update.clear();
	}

	private void processRemovals() {
		removed.forEach(this::processRemoval);
		removed.clear();
	}

	private void processRemoval(Widget removed) {
		Iterator<Entry> entries = this.entries.iterator();
		while (entries.hasNext()) {
			Entry entry = entries.next();
			if (entry.getWidget() == removed) {
				entries.remove();
				entry.unregister();
			}
		}
	}

	protected abstract Entry createEntry(Widget widget);

	protected abstract Style getStyle();

	protected abstract int getMaxLineLength();

}
