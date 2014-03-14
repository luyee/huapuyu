package com.anders.dp.责任链.example;


public class 列表FilterProcessor {
	private String msg;
	private 列表Filter fc;

	public String process() {
		return fc.doFilter(msg);
	}

	// getters and setters

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public 列表Filter getFc() {
		return fc;
	}

	public void setFc(列表Filter fc) {
		this.fc = fc;
	}
}
