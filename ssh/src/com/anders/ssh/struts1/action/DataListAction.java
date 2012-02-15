package com.anders.ssh.struts1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.anders.ssh.struts1.form.DataListForm;

public class DataListAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataListForm dataListForm = (DataListForm) form;

		if (StringUtils.isNotBlank(dataListForm.getName()) && dataListForm.getName().trim().equals("zhuzhen")) {
			return mapping.findForward("success");
		}
		else {
			return mapping.findForward("failure");
		}
	}
}