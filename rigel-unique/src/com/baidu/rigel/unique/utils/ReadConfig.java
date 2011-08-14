package com.baidu.rigel.unique.utils;

import org.springframework.stereotype.Component;

@Component("readConfig")
public class ReadConfig {
	private int days = 75;

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
}
