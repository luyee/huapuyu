package struts.action;

import java.util.List;

import model.test.Tb_depart;
import service.interf.ITb_departService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class Tb_departAction extends ActionSupport
{
	private ITb_departService service;
	private List<Tb_depart> departs;
	private int id;
	private Tb_depart model;

	// @Transactional
	@SuppressWarnings("unchecked")
	public String execute()
	{
		departs = service.getAll();
		return SUCCESS;
	}

	public ITb_departService getService()
	{
		return service;
	}

	public void setService(ITb_departService service)
	{
		this.service = service;
	}

	public List<Tb_depart> getDeparts()
	{
		return departs;
	}

	public void setDeparts(List<Tb_depart> departs)
	{
		this.departs = departs;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Tb_depart getModel()
	{
		return model;
	}

	public void setModel(Tb_depart model)
	{
		this.model = model;
	}
}
