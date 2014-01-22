package com.anders.crm.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.anders.crm.vo.RegisterIndividualVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-security.xml", "classpath:dispatcher-servlet.xml" })
public class RegisterControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

	private final MockHttpServletRequest request = new MockHttpServletRequest();
	private final MockHttpServletResponse response = new MockHttpServletResponse();

	@Autowired
	private RegisterController controller;
	@Autowired
	// private RequestMappingHandlerAdapter handlerAdapter;
	private AnnotationMethodHandlerAdapter handlerAdapter;

	// @BeforeClass
	// public static void setUp() {
	// if (handlerMapping == null) {
	// String[] configs = { "file:/crm/src/main/webapp/WEB-INF/dispatcher-servlet.xml" };
	// XmlWebApplicationContext context = new XmlWebApplicationContext();
	// context.setConfigLocations(configs);
	// MockServletContext mockServletContext = new MockServletContext();
	// context.setServletContext(mockServletContext);
	// context.refresh();
	// mockServletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
	// handlerMapping = (HandlerMapping) context.getBean(DefaultAnnotationHandlerMapping.class);
	// handlerAdapter = (HandlerAdapter) context.getBean(context.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);
	// }
	// }

	@Test
	public void test1() throws NoSuchMethodException, Exception {
		request.setRequestURI("/register.do");
		request.setMethod(HttpMethod.POST.name());
		HttpSession session = request.getSession();

		List<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
		ga.add(new GrantedAuthorityImpl("ROLE_USER"));
		User user = new User("zhuzhen", "1234567", true, true, true, true, ga);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

		RegisterIndividualVO registerIndividualVO = new RegisterIndividualVO();
		registerIndividualVO.setUsername("test1");
		registerIndividualVO.setName("test1");
		registerIndividualVO.setPassword("123456");
		registerIndividualVO.setEmail("test1@gmail.com");

		// ModelAndView mav = handlerAdapter.handle(request, response, new HandlerMethod(controller, "register", Model.class, HttpServletRequest.class));
		// ModelAndView mav = handlerAdapter.handle(request, response, new HandlerMethod(controller, "registerIndividual", RegisterIndividualVO.class));
		ModelAndView mav = handlerAdapter.handle(request, response, new HandlerMethod(controller, "registerIndividual", RegisterIndividualVO.class));

	}
}
