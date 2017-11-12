package com.ulfric.plugin.widgets;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ulfric.plugin.widgets.text.Text;

public class HELLO_WORLD_DELETE_THIS implements Listener {

	@EventHandler
	public void on(PlayerJoinEvent event) {
		Dashboards.getDashboards(event.getPlayer()).addWidget(new ExampleWidget1());
		Dashboards.getDashboards(event.getPlayer()).addWidget(new ExampleWidget2());
		Dashboards.getDashboards(event.getPlayer()).addWidget(new ExampleWidget3());
		Dashboards.getDashboards(event.getPlayer()).addWidget(new ExampleWidget4());
		Dashboards.getDashboards(event.getPlayer()).addWidget(new ExampleWidget5());
		Dashboards.getDashboards(event.getPlayer()).addWidget(new ExampleWidget6());
	}

	public static class ExampleWidget1 extends Widget {
		public ExampleWidget1() {
			super(StandardDashboardType.TAB);
		}

		@Override
		public Text apply(Player player) {
			Text text = new Text();
			text.setTitle("BARATHEON");
			text.setBody(Arrays.asList("GODS I WAS STRONG THEN"));
			return text;
		}
	}

	public static class ExampleWidget2 extends Widget {
		public ExampleWidget2() {
			super(StandardDashboardType.TAB);
		}

		@Override
		public Text apply(Player player) {
			Text text = new Text();
			text.setTitle("BARATHEON");
			text.setBody(Arrays.asList("THE WHOOOOORE IS PREGNANT"));
			return text;
		}
	}

	public static class ExampleWidget3 extends Widget {
		public ExampleWidget3() {
			super(StandardDashboardType.TAB);
		}

		@Override
		public Text apply(Player player) {
			Text text = new Text();
			text.setTitle("BARATHEON");
			text.setBody(Arrays.asList("CAREFUL NED, CAREFUL"));
			return text;
		}
	}

	public static class ExampleWidget4 extends Widget {
		public ExampleWidget4() {
			super(StandardDashboardType.TAB);
		}

		@Override
		public Text apply(Player player) {
			Text text = new Text();
			text.setTitle("BARATHEON");
			text.setBody(Arrays.asList("BOW YOU SHITS"));
			return text;
		}
	}

	public static class ExampleWidget5 extends Widget {
		public ExampleWidget5() {
			super(StandardDashboardType.TAB);
		}

		@Override
		public Text apply(Player player) {
			Text text = new Text();
			text.setTitle("BARATHEON");
			text.setBody(Arrays.asList("THANK THE GODS FOR BESSIE, AND HER GREAT BIG TITS"));
			return text;
		}
	}

	public static class ExampleWidget6 extends Widget {
		public ExampleWidget6() {
			super(StandardDashboardType.TAB);
		}

		@Override
		public Text apply(Player player) {
			Text text = new Text();
			text.setTitle("BARATHEON");
			text.setBody(Arrays.asList("WEAR IT IN SILENCE, OR I'LL HONOR YOU AGAIN"));
			return text;
		}
	}

}
