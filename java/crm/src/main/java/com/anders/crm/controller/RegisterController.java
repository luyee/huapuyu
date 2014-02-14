package com.anders.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.anders.crm.bo.User;
import com.anders.crm.facade.MailFacade;
import com.anders.crm.utils.Constant;
import com.anders.crm.utils.MailType;
import com.anders.crm.utils.SecurityUtil;
import com.anders.crm.vo.RegisterIndividualVO;

/**
 * 注册控制器
 * 
 * @author Anders Zhu
 * 
 */
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

	/**
	 * 注册个人用户展示
	 */
	@RequestMapping(value = "/register_individual.do", method = { RequestMethod.GET })
	public ModelAndView registerIndividual() {
		ModelAndView modelAndView = new ModelAndView("register/register_individual");
		return modelAndView;
	}

	/**
	 * 注册个人用户操作
	 */
	@RequestMapping(value = "/register_individual.do", method = { RequestMethod.POST })
	public ModelAndView registerIndividual(HttpServletRequest request, RegisterIndividualVO registerIndividualVO) {
		User user = new User();
		BeanUtils.copyProperties(registerIndividualVO, user);
		user.setAddUser(getUserService().getUserByUsername(Constant.ADMINISTRATOR_USERNAME));
		user.setPassword(SecurityUtil.getSha256Password(user.getPassword(), user.getUsername()));

		getUserService().save(user);

		// TODO Anders Zhu : 将成功注册的信息的放到消息队列中异步通知用户注册成功

		String from = getMessage("email.from", request);
		String subject = getMessage("register_individual.email.subject", request);
		String remark = getMessage("register_individual.email.remark", request);

		Map<String, Object> emailParams = new HashMap<String, Object>();
		emailParams.put(MailFacade.PARAM_REMARK, remark);
		getMailFacade().sendMail(MailType.REGISTER_INDIVIDUAL, from, user.getEmail(), subject, emailParams);

		return new ModelAndView("redirect:index.do");
	}
}
