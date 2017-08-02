package com.ulfric.monty;

import com.ulfric.commons.value.Bean;

public class Options extends Bean {

	private StandardStyles style; // TODO support Style interface

	public StandardStyles getStyle() {
		return style;
	}

	public void setStyle(StandardStyles style) {
		this.style = style;
	}

}
