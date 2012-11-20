package com.anders.ssh.struts1.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.anders.ssh.model.xml.Data;
import com.anders.ssh.struts1.form.DataListForm;

public class DataListAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataListForm dataListForm = (DataListForm) form;

		if (CollectionUtils.isNotEmpty(dataListForm.getDataList()))
			System.out.println(dataListForm.getDataList().size());

		List<Data> dataList = new ArrayList<Data>();
		Data data1 = new Data();
		data1.setId(2L);
		data1.setName("tom");
		Data data2 = new Data();
		data2.setId(4L);
		data2.setName("john");
		dataList.add(data1);
		dataList.add(data2);

		if (StringUtils.isNotBlank(dataListForm.getName()) && dataListForm.getName().trim().equals("zhuzhen")) {
			request.getSession().setAttribute("dataList", dataList);
			request.setAttribute("dataList", dataList);
			return mapping.findForward("success");
		}
		else {
			return mapping.findForward("failure");
		}
	}
}