package com.ulfric.monty.text;

import com.ulfric.commons.value.Bean;

import java.util.List;

public class Text extends Bean {

	private String title;
	private List<String> body;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getBody() {
		return body;
	}

	public void setBody(List<String> body) {
		this.body = body;
	}

}
