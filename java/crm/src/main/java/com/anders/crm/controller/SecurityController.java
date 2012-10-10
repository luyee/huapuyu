package com.anders.crm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.anders.crm.bo.User;
import com.anders.crm.utils.Constant;
import com.anders.crm.utils.SecurityUtil;
import com.anders.crm.vo.GetPasswordVO;
import com.anders.crm.vo.RegisterIndividualVO;

/**
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
			modelAndView.addObject("errorMsg", getMessageSource().getMessage("login.error_msg", null, request.getLocale()));
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

	@RequestMapping(value = "/register_individual.do", method = { RequestMethod.GET })
	public ModelAndView registerIndividual() {
		ModelAndView modelAndView = new ModelAndView("register/register_individual");
		return modelAndView;
	}

	@RequestMapping(value = "/register_individual.do", method = { RequestMethod.POST })
	public ModelAndView registerIndividual(RegisterIndividualVO registerIndividualVO) {
		ModelAndView modelAndView = new ModelAndView("register/register_individual");

		User user = new User();
		BeanUtils.copyProperties(registerIndividualVO, user);
		user.setAddUser(getUserService().getUserByUsername(Constant.ADMINISTRATOR_USERNAME));
		user.setPassword(SecurityUtil.getSha256Password(user.getPassword(), user.getUsername()));

		getUserService().save(user);

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
	public ModelAndView getPassword(GetPasswordVO getPasswordVO) {
		ModelAndView modelAndView = new ModelAndView("get_password");

		getUserService().updatePasswordToDefault(getPasswordVO.getUsername());

		return modelAndView;
	}
}
