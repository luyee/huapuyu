package com.anders.crm.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.anders.crm.bo.User;
import com.anders.crm.exception.SecurityCodeException;
import com.anders.crm.service.UserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.gson.Gson;

/**
 * Base Controller
 * 
 * @author Anders Zhu
 * 
 */
public abstract class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private final static String AJAX_FIELD_ID = "fieldId";
	private final static String AJAX_FIELD_VALUE = "fieldValue";
	private final static String SECURITY_CODE_INPUT_ID = "securityCode";

	@Autowired
	private UserService userService;
	// TODO Anders Zhu : 了解这些注解
	// @Autowired
	// @Qualifier
	// @Value
	// @Required
	// @Resource(type = DelegatingMessageSource.class)
	@Autowired
	private DelegatingMessageSource messageSource;
	@Autowired
	private Producer captchaProducer;

	/**
	 * AJAX统一调用方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	// 原来使用@ResponseBody注解，ajaxIsExist返回字符串，但是发现中文字符串出现乱码，所以只能使用下面的方式
	// @ResponseBody
	@RequestMapping(value = "/ajaxIsExist.do")
	public void ajaxIsExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fieldName = request.getParameter(AJAX_FIELD_ID);
		String fieldValue = request.getParameter(AJAX_FIELD_VALUE);

		Object[] resultObjects = new Object[3];
		resultObjects[0] = fieldName;
		resultObjects[1] = false;
		resultObjects[2] = messageSource.getMessage("ajax.error", null, request.getLocale());

		// 用户名
		if (User.USERNAME.equalsIgnoreCase(fieldName)) {
			boolean isExist = userService.isExistByUsername(fieldValue);
			String alertText = isExist ? messageSource.getMessage("ajax.is_exist.user_name", null, request.getLocale()) : messageSource.getMessage("ajax.is_not_exist.user_name", null, request.getLocale());

			resultObjects[1] = isExist;
			resultObjects[2] = alertText;
		}
		// 验证码
		else if (SECURITY_CODE_INPUT_ID.equalsIgnoreCase(fieldName)) {
			String securityCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (StringUtils.isBlank(securityCode)) {
				throw new SecurityCodeException("securityCode is blank");
			}
			boolean isExist = securityCode.equals(fieldValue);
			String alertText = isExist ? messageSource.getMessage("ajax.right.security_code", null, request.getLocale()) : messageSource.getMessage("ajax.wrong.security_code", null, request.getLocale());

			resultObjects[1] = isExist;
			resultObjects[2] = alertText;
		}

		Gson gson = new Gson();
		String json = gson.toJson(resultObjects);
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_security_code.do")
	public void getSecurityCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

		String capText = captchaProducer.createText();
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		}
		finally {
			out.close();
		}
	}

	// TODO Anders Zhu : 测试方法，需要删除
	public void test(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
	}

	public DelegatingMessageSource getMessageSource() {
		return messageSource;
	}

	public UserService getUserService() {
		return userService;
	}
}
