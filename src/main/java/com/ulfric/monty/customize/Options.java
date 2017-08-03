package com.ulfric.monty.customize;

import com.ulfric.commons.value.Bean;
import com.ulfric.monty.text.StandardStyles;

public class Options extends Bean {

	private StandardStyles style; // TODO support Style interface

	public StandardStyles getStyle() {
		return style;
	}

	public void setStyle(StandardStyles style) {
		this.style = style;
	}

}
