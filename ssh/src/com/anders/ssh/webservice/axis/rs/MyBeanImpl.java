package com.anders.ssh.webservice.axis.rs;

public class MyBeanImpl implements MyBean {

	String str = null;

	public void setVal(String s) {
		str = s;
	}

	public String emerge() {
		return str;
	}
}
