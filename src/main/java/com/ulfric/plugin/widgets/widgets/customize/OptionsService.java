package com.ulfric.plugin.widgets.widgets.customize;

import com.ulfric.plugin.services.Service;
import com.ulfric.plugin.widgets.scoreboard.text.ScoreboardStyle;
import com.ulfric.plugin.widgets.tab.text.TabStyle;

import java.util.UUID;

public class OptionsService implements Service<OptionsService> {

	public static OptionsService get() {
		return Service.get(OptionsService.class);
	}

	@Override
	public Class<OptionsService> getService() {
		return OptionsService.class;
	}

	public Options getOptions(UUID uniqueId) { // TODO actually allow, use database
		Options options = new Options();

		options.setScoreboardStyle(ScoreboardStyle.FLAT);
		options.setTabStyle(TabStyle.FLAT);

		return options;
	}

}