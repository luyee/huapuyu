package com.anders.crm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Anders Zhu
 * 
 */
@Controller
public class IndexController {
	@RequestMapping(value = "/index.do", method = { RequestMethod.GET })
	public ModelAndView helloWorld(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		List<String> list = new ArrayList<String>();
		list.add("zhuzhen");
		list.add("guolili");
		modelAndView.addObject("list", list);
		modelAndView.addObject("name", "My First Spring Mvc");
		return modelAndView;
	}
}
