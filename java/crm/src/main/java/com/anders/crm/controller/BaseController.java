package com.anders.crm.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anders.crm.bo.User;
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

	protected final static String AJAX_FIELD_ID = "fieldId";
	protected final static String AJAX_FIELD_VALUE = "fieldValue";

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceBundleMessageSource rbms;
	@Autowired
	private Producer captchaProducer;

	@ResponseBody
	@RequestMapping(value = "/ajaxIsExist.do")
	public String ajaxIsExist(HttpServletRequest request) {
		String fieldName = request.getParameter(AJAX_FIELD_ID);
		String fieldValue = request.getParameter(AJAX_FIELD_VALUE);

		Object[] resultObjects = new Object[5];
		resultObjects[0] = fieldName;
		resultObjects[1] = false;
		resultObjects[2] = StringUtils.EMPTY;
		resultObjects[3] = "asfasdfas";
		resultObjects[4] = "2222";

		if (User.USERNAME.equalsIgnoreCase(fieldName)) {
			boolean isExist = userService.isExistByUsername(fieldValue);
			String alertText = isExist ? rbms.getMessage("ajax.is_exist.username", null, request.getLocale()) : rbms.getMessage("ajax.is_not_exist.username", null, request.getLocale());

			resultObjects[1] = isExist;
			resultObjects[2] = alertText;
		}

		Gson gson = new Gson();
		return gson.toJson(resultObjects);
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

	public ResourceBundleMessageSource getRbms() {
		return rbms;
	}

	public void setRbms(ResourceBundleMessageSource rbms) {
		this.rbms = rbms;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
