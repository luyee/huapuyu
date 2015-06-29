package session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import entity.User;

@Name("userList")
public class UserList extends EntityQuery<User> {
	private static final long serialVersionUID = 5726614121594770971L;

	public UserList() {
		setEjbql("select user from User user");
	}
}
