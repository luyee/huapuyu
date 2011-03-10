package security1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class SecureResourceFilterInvocationDefinitionSource extends AbstractAuthenticationProcessingFilter
{

	@Override
	public Authentication attemptAuthentication(HttpServletRequest arg0, HttpServletResponse arg1) throws AuthenticationException, IOException, ServletException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
