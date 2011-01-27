package session;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import entity.User;

@Name("authenticator")
public class Authenticator
{
	@Logger
	private Log log;

	@In
	Identity identity;

	@In
	Credentials credentials;

	@In
	EntityManager entityManager;

	public boolean authenticate()
	{
		log.info("authenticating {0}", credentials.getUsername());

		Query query = entityManager.createQuery("from User user where user.name = 'hello' and user.pwd = '123456'");
		List<?> modelList = query.getResultList();
		User user = new User();
		if (modelList != null && modelList.size() > 0)
			user = (User) modelList.get(0);

		if (user.getName().equals(credentials.getUsername()) && user.getPwd().equals(credentials.getPassword()))
		{
			identity.addRole("admin");
			return true;
		}

		return false;
	}
}
