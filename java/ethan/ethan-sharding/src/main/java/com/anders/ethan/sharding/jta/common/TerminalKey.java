package com.anders.ethan.sharding.jta.common;

import java.io.Serializable;

import com.anders.ethan.sharding.jta.util.CommonUtils;

public class TerminalKey implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	private String application = "unspecified";
	private String endpoint = "unspecified";

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public TerminalKey clone() throws CloneNotSupportedException {
		TerminalKey that = new TerminalKey();
		that.setApplication(this.application);
		that.setEndpoint(this.endpoint);
		return that;
	}

	public int hashCode() {
		int hash = 23;
		hash += 29 * (this.application == null ? 0 : this.application.hashCode());
		hash += 31 * (this.endpoint == null ? 0 : this.endpoint.hashCode());
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (TerminalKey.class.equals(obj.getClass()) == false) {
			return false;
		}

		TerminalKey that = (TerminalKey) obj;
		boolean appEquals = CommonUtils.equals(this.application, that.application);
		boolean endEquals = CommonUtils.equals(this.endpoint, that.endpoint);
		return appEquals && endEquals;
	}

	public String toString() {
		return String.format("terminal: application= %s, endpoint= %s", this.application, this.endpoint);
	}

}
