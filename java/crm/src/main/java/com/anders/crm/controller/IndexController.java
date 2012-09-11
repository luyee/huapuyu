package com.anders.crm.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
		ServletContext servletContext = request.getSession().getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("authenticated", SecurityUtil.isAuthenticated());
		modelAndView.addObject("username", SecurityUtil.getUsername());
		// System.out.println(Locale.getDefault());
		// System.out.println(Locale.getISOCountries());
		// System.out.println(Locale.getISOLanguages());
		// System.out.println(request.getLocale());
		return modelAndView;
	}
}
