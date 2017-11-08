package com.ulfric.plugin.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections4.SetUtils;
import org.bukkit.entity.Player;

import com.ulfric.commons.collection.MapHelper;
import com.ulfric.plugin.widgets.text.Style;
import com.ulfric.plugin.widgets.text.Text;
import com.ulfric.plugin.widgets.text.TextHelper;
import com.ulfric.plugin.widgets.text.graph.Entry;
import com.ulfric.plugin.widgets.widgets.customize.Options;
import com.ulfric.plugin.widgets.widgets.customize.OptionsService;

public abstract class Dashboard {

	private static final ConcurrentMap<UUID, List<Dashboard>> DASHBOARDS = MapHelper.newConcurrentMap(2);

	static Collection<List<Dashboard>> getAllDashboardsMutableView() { // TODO better solution
		return DASHBOARDS.values();
	}

	public static List<Dashboard> getDashboards(Player player) {
		return getDashboards(player.getUniqueId());
	}

	public static List<Dashboard> getDashboards(UUID player) {
		List<Dashboard> dashboards = DASHBOARDS.get(player);

		if (dashboards == null) {
			return Collections.emptyList();
		}

		return dashboards;
	}

	protected final Player viewer;
	protected final Options options;
	protected final Set<Class<? extends Widget>> update = SetUtils.newIdentityHashSet();
	protected final Set<Widget> removed = SetUtils.newIdentityHashSet();
	protected final List<Entry> entries = new ArrayList<>();

	public Dashboard(Player viewer) {
		Objects.requireNonNull(viewer, "viewer");

		this.viewer = viewer;
		this.options = OptionsService.get().getOptions(viewer.getUniqueId()); // TODO handle failures

		register();
	}

	protected void register() {
		DASHBOARDS.computeIfAbsent(viewer.getUniqueId(), ignore -> new CopyOnWriteArrayList<>())
			.add(this);
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
		Style style = getStyle();
		for (Entry entry : entries) {
			for (Class<? extends Widget> update : this.update) {
				Widget widget = entry.getWidget();
				if (!update.isInstance(widget)) {
					continue;
				}

				Text text = widget.apply(viewer);
				List<String> content = text == null ? Collections.emptyList() : style.apply(text);
				content = TextHelper.wrap(content, 32); // TODO support 48
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

}
