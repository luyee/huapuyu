package com.anders.crm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.anders.crm.utils.SecurityUtil;

/**
 * 
 * @author Anders Zhu
 * 
 */
@Controller
public class IndexController {
	@RequestMapping(value = "/index.do", method = { RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("authenticated", SecurityUtil.isAuthenticated());
		modelAndView.addObject("username", SecurityUtil.getUsername());
		return modelAndView;
	}
}
