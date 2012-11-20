package com.anders.ssh.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloWorldController extends AbstractController {
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		ModelAndView modelAndView = new ModelAndView("helloWorld");
		List<String> list = new ArrayList<String>();
		list.add("zhuzhen");
		list.add("guolili");
		modelAndView.addObject("list", list);
		return modelAndView;
	}

}
