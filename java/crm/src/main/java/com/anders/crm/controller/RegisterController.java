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
import com.anders.crm.vo.RegisterIndividualVO;

@Controller
public class RegisterController extends BaseController {

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
}
