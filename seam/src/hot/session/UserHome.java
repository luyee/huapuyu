package session;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import entity.User;

@Name("userHome")
public class UserHome extends EntityHome<User> {
	private static final long serialVersionUID = -3211501328545469051L;

	@RequestParameter
	Long userId;

	@Override
	public Object getId() {
		if (userId == null) {
			return super.getId();
		} else {
			return userId;
		}
	}

	@Override
	@Begin
	public void create() {
		super.create();
	}
}
