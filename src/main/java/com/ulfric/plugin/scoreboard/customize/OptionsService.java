package com.ulfric.plugin.scoreboard.customize;

import com.ulfric.plugin.scoreboard.text.StandardStyles;
import com.ulfric.plugin.services.Service;

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

		options.setStyle(StandardStyles.FLAT);

		return options;
	}

}