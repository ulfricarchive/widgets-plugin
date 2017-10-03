package com.ulfric.plugin.scoreboard.customize;

import com.ulfric.commons.value.Bean;
import com.ulfric.plugin.scoreboard.text.StandardStyles;

public class Options extends Bean {

	private StandardStyles style; // TODO support Style interface

	public StandardStyles getStyle() {
		return style;
	}

	public void setStyle(StandardStyles style) {
		this.style = style;
	}

}
