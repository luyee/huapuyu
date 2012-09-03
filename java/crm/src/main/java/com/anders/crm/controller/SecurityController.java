package com.anders.crm.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.anders.crm.service.UserService;
import com.anders.crm.vo.GetPasswordVO;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 
 * @author Anders Zhu
 * 
 */
@Controller
public class SecurityController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private Producer captchaProducer;

	private final static String LOGIN_FAILED = "1";

	@RequestMapping(value = "/login.do", method = { RequestMethod.GET })
	public ModelAndView login(HttpServletRequest request) {
		String error = request.getParameter("error");
		ModelAndView modelAndView = new ModelAndView("login");
		if (LOGIN_FAILED.equals(error)) {
			modelAndView.addObject("errorMsg", getRbms().getMessage("login.error_msg", null, request.getLocale()));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/logout.do", method = { RequestMethod.GET })
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("logout");
		List<String> list = new ArrayList<String>();
		list.add("zhuzhen");
		list.add("guolili");
		modelAndView.addObject("list", list);
		modelAndView.addObject("name", "My First Spring Mvc");
		return modelAndView;
	}

	@RequestMapping(value = "/register.do", method = { RequestMethod.GET })
	public ModelAndView register(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("register");
		List<String> list = new ArrayList<String>();
		list.add("zhuzhen");
		list.add("guolili");
		modelAndView.addObject("list", list);
		modelAndView.addObject("name", "My First Spring Mvc");
		return modelAndView;
	}

	@RequestMapping(value = "/get_code.do")
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

	@RequestMapping(value = "/get_password.do", method = { RequestMethod.GET })
	public ModelAndView getPassword(HttpServletRequest request) {
		String capText = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isBlank(capText)) {
			throw new RuntimeException("security code is blank");
		}

		ModelAndView modelAndView = new ModelAndView("get_password");
		modelAndView.addObject("defaultCode", capText);
		return modelAndView;
	}

	@RequestMapping(value = "/get_password.do", method = { RequestMethod.POST })
	// public ModelAndView getPassword(@ModelAttribute("getPasswordVO") GetPasswordVO getPasswordVO) {
	// public ModelAndView getPassword(@ModelAttribute GetPasswordVO getPasswordVO) {
	public ModelAndView getPassword(GetPasswordVO getPasswordVO) {
		ModelAndView modelAndView = new ModelAndView("get_password");

		userService.updatePasswordToDefault(getPasswordVO.getUsername());

		return modelAndView;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Producer getCaptchaProducer() {
		return captchaProducer;
	}

	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}

}
