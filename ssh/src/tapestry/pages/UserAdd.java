package tapestry.pages;

import model.test.Tb_user;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import service.interf.ITb_userService;

public class UserAdd
{
	@Property
	private Tb_user user = new Tb_user("zhuzhen", "123");

	@Inject
	private ITb_userService userService;

	// public Tb_user getUser()
	// {
	// return user;
	// }
	//
	// public void setUser(Tb_user user)
	// {
	// this.user = user;
	// }

	public void onSuccess()
	{
		userService.save(user);
	}
}
