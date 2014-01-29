package com.anders.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import com.anders.crm.utils.SecurityUtil;
import com.anders.crm.vo.RegisterIndividualVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-security.xml", "classpath:dispatcher-servlet.xml" })
public class RegisterControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

	private final MockHttpServletRequest request = new MockHttpServletRequest();
	private final MockHttpServletResponse response = new MockHttpServletResponse();

	@Autowired
	// private RequestMappingHandlerAdapter handlerAdapter;
	private AnnotationMethodHandlerAdapter handlerAdapter;
	@Autowired
	private DefaultAnnotationHandlerMapping handlerMapping;

	@Test
	@Rollback(value = true)
	public void test1() throws NoSuchMethodException, Exception {
		request.setRequestURI("/register_individual.do");
		request.setMethod(HttpMethod.POST.name());
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);

		String username = SecurityUtil.getRandomUsername();
		Map<String, String> map = new HashMap<String, String>();
		map.put(RegisterIndividualVO.USERNAME, username);
		map.put(RegisterIndividualVO.PASSWORD, "123456");
		map.put(RegisterIndividualVO.NAME, username);
		map.put(RegisterIndividualVO.EMAIL, username + "@hhcrm.com");
		request.setParameters(map);

		HttpSession session = request.getSession();

		List<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
		ga.add(new GrantedAuthorityImpl("ROLE_USER"));
		User user = new User("zhuzhen", "123456", true, true, true, true, ga);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

		HandlerExecutionChain chain = handlerMapping.getHandler(request);

		ModelAndView mav = handlerAdapter.handle(request, response, chain.getHandler());

	}
}
