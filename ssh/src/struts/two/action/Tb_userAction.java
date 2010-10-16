package struts.two.action;

import java.util.List;

import model.test.Tb_user;
import service.interf.ITb_userService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class Tb_userAction extends ActionSupport {
	private ITb_userService service;
	private List<Tb_user> users;
	private int id;
	private Tb_user model;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tb_user getModel() {
		return model;
	}

	public void setModel(Tb_user model) {
		this.model = model;
	}

	public ITb_userService getService() {
		return service;
	}

	public void setService(ITb_userService service) {
		this.service = service;
	}

	public List<Tb_user> getUsers() {
		return users;
	}

	public void setUsers(List<Tb_user> users) {
		this.users = users;
	}

	// @Transactional
	@SuppressWarnings("unchecked")
	public String list() {
		users = service.getAll();
		return SUCCESS;
	}

	public String add() {
		return INPUT;
	}

	public String addInput() {
		service.save(model);
		return SUCCESS;
	}

	public String update() {
		service.update(model);
		return SUCCESS;
	}

	public String updateInput() {
		this.model = service.getById(id);
		return INPUT;
	}

	public String delete() {
		service.deleteById(id);
		return SUCCESS;
	}
}
