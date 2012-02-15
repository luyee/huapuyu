package com.anders.ssh.struts1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.RequestProcessor;

public class EncodingProcessor extends RequestProcessor {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("GB2312");
		response.setContentType("text/html;charset=gb2312");
		super.process(request, response);
	}

}
