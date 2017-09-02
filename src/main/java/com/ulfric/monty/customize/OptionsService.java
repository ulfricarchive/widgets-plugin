package com.ulfric.monty.customize;

import com.ulfric.data.database.Database;
import com.ulfric.data.database.Store;
import com.ulfric.servix.Service;

import java.util.UUID;

public class OptionsService implements Service<OptionsService> {

	public static OptionsService get() {
		return Service.get(OptionsService.class);
	}

	@Database
	private Store options;

	@Override
	public Class<OptionsService> getService() {
		return OptionsService.class;
	}

	public Options getOptions(UUID uniqueId) {
		return options.getData(uniqueId).getAs(Options.class);
	}

}