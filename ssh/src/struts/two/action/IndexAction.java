package struts.two.action;

import java.util.List;

import model.test.Tb_user;
import service.interf.IIndexService;

import com.opensymphony.xwork2.Action;

import dao.interf.ITb_userDao;

public class IndexAction implements Action
{
	private IIndexService is;
	private List<Tb_user> users;
	private ITb_userDao dao;

	public List<Tb_user> getUsers()
	{
		return users;
	}

	public void setUsers(List<Tb_user> users)
	{
		this.users = users;
	}

	public ITb_userDao getDao()
	{
		return dao;
	}

	public void setDao(ITb_userDao dao)
	{
		this.dao = dao;
	}

	public IIndexService getIs()
	{
		return is;
	}

	public void setIs(IIndexService is)
	{
		this.is = is;
	}

	// @Transactional
	@SuppressWarnings("unchecked")
	public String execute()
	{
		// if (is.valid())
		// {
		// return SUCCESS;
		// }
		// else
		// {
		// return ERROR;
		// }

		// this.users = dao.getAll();

		Tb_user model = new Tb_user();
		model.setId(100);
		model.setName("tom");
		model.setPwd("123");

		dao.save(model);

		// dao.save(model);

		model.setName("jack");

		users = dao.getAll();

		System.out.println(users.size());

		for (Tb_user model1 : users)
		{
			System.out.println(model1.getId());
		}

		return "list";
	}
}
