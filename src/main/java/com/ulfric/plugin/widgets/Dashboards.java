package com.ulfric.plugin.widgets;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections4.MapUtils;
import org.bukkit.entity.Player;

import com.ulfric.commons.collection.MapHelper;
import com.ulfric.dragoon.reflect.Classes;
import com.ulfric.plugin.widgets.widgets.customize.Options;
import com.ulfric.plugin.widgets.widgets.customize.OptionsService;

public final class Dashboards {

	static final ConcurrentMap<UUID, Dashboards> DASHBOARDS = MapHelper.newConcurrentMap(2);

	static Collection<Dashboards> getAllDashboardsMutableView() { // TODO better solution
		return DASHBOARDS.values();
	}

	public static Dashboards getDashboards(Player player) {
		return DASHBOARDS.computeIfAbsent(player.getUniqueId(), ignore -> new Dashboards(player));
	}

	public static void clearDashboards(Player player) {
		DASHBOARDS.remove(player.getUniqueId());
	}

	private final Player viewer;
	private final Options options;
	private final List<Dashboard> dashboards = new CopyOnWriteArrayList<>();

	private Dashboards(Player viewer) {
		this.viewer = viewer;

		this.options = OptionsService.get().getOptions(viewer.getUniqueId()); // TODO handle failures
	}

	public void update() {
		dashboards.forEach(dashboard -> {
			dashboard.queueUpdate(TimerWidget.class);
			dashboard.update();
		});
	}

	public Player getViewer() {
		return viewer;
	}

	public Options getOptions() {
		return options;
	}

	void add(Dashboard dashboard) {
		dashboards.add(dashboard);
	}

	public <T extends Dashboard> T getDashboardByType(Class<T> type) {
		Objects.requireNonNull(type, "type");

		for (Dashboard dashboard : this.dashboards) {
			if (type.isInstance(dashboard)) {
				return type.cast(dashboard);
			}
		}

		return null;
	}

	public <T extends Dashboard> void removeDashboardsByType(Class<T> type) {
		Objects.requireNonNull(type, "type");

		dashboards.removeIf(type::isInstance);
	}

	public void addWidget(Widget widget) {
		Map<Class<? extends Widget>, StandardDashboardType> widgetDisplays = options.getWidgets();

		DashboardType type = null;
		if (MapUtils.isNotEmpty(widgetDisplays)) {
			type = widgetDisplays.get(Classes.getNonDynamic(widget.getClass()));
		}

		if (type == null) {
			type = widget.getDefaultDashboard();
		}

		Dashboard dashboard = type.getDashboard(this);
		if (dashboard == null) {
			return;
		}

		dashboard.add(widget);
	}

	public void queueUpdate(Class<? extends Widget> widget) {
		dashboards.forEach(dashboard -> dashboard.queueUpdate(widget));
	}

}
