package com.anders.ssh.struts1.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.anders.ssh.model.xml.Data;
import com.anders.ssh.struts1.utils.AutoArrayList;

public class DataListForm extends ActionForm {

	private static final long serialVersionUID = 4774961899721712545L;

	private String name;
	// private List dataList = new AutoArrayList(Data.class);
	private List<Data> dataList = new AutoArrayList<Data>(Data.class);

	// public DataListForm() {
	// Factory factory = new Factory() {
	// public Object create() {
	// return new Data();
	// }
	// };
	// this.dataList = LazyList.decorate(new ArrayList<Data>(), factory);
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Data> getDataList() {
		return dataList;
	}

	public void setDataList(List<Data> dataList) {
		this.dataList = dataList;
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