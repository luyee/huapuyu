package com.anders.ssh.struts1.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class DataListForm extends ActionForm {

	private static final long serialVersionUID = 4774961899721712545L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (StringUtils.isBlank(name))
			errors.add("name", new ActionMessage("data.error"));
		return errors;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.name = null;
	}
}