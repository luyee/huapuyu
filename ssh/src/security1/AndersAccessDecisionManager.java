package security1;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

public class AndersAccessDecisionManager implements AccessDecisionManager
{

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supports(ConfigAttribute attribute)
	{
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return false;
	}

}
