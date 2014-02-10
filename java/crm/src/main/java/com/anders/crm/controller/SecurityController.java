package com.anders.crm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.anders.crm.vo.GetPasswordVO;

/**
 * 安全控制器
 * 
 * @author Anders Zhu
 * 
 */
@Controller
public class SecurityController extends BaseController {

	private final static String LOGIN_FAILED = "1";

	@RequestMapping(value = "/login.do", method = { RequestMethod.GET })
	public ModelAndView login(HttpServletRequest request) {
		String error = request.getParameter("error");
		ModelAndView modelAndView = new ModelAndView("login");
		if (LOGIN_FAILED.equals(error)) {
			modelAndView.addObject("errorMsg", getMessage("login.error_msg", request));
		}
		return modelAndView;
	}

	@Deprecated
	// @RequestMapping(value = "/logout.do", method = { RequestMethod.GET })
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("logout");
		List<String> list = new ArrayList<String>();
		list.add("zhuzhen");
		list.add("guolili");
		modelAndView.addObject("list", list);
		modelAndView.addObject("name", "My First Spring Mvc");
		return modelAndView;
	}

	/**
	 * 显示找回密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get_password.do", method = { RequestMethod.GET })
	public ModelAndView getPassword(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("get_password");
		return modelAndView;
	}

	/**
	 * 找回密码提交操作
	 * 
	 * @param getPasswordVO
	 * @return
	 */
	@RequestMapping(value = "/get_password.do", method = { RequestMethod.POST })
	// public ModelAndView getPassword(@ModelAttribute("getPasswordVO") GetPasswordVO getPasswordVO) {
	// public ModelAndView getPassword(@ModelAttribute GetPasswordVO getPasswordVO) {
	public ModelAndView getPassword(HttpServletRequest request, GetPasswordVO getPasswordVO) {
		ModelAndView modelAndView = new ModelAndView("get_password");

		String from = getMessage("email.from", request);
		String subject = getMessage("get_password.email.subject", request);

		getUserService().updatePasswordToDefault(getPasswordVO.getUsername(), from, subject);
		return modelAndView;
	}
}
