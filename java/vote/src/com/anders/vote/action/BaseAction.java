package com.anders.vote.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport implements SessionAware, RequestAware {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> session;
	private Map<String, Object> request;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

}
