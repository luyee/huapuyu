package com.anders.vote.action;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsSpringTestCase;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

public class IndexActionTest extends StrutsSpringTestCase {

	@Test
	public void testForTest1() {
		request.setParameter("user", "guolili");
		ActionProxy proxy = getActionProxy("/forTest.do");
		IndexAction action = (IndexAction) proxy.getAction();
		String result = StringUtils.EMPTY;
		try {
			System.out.println(resourceLoader.toString());
			result = proxy.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(ActionSupport.SUCCESS, result);
		assertEquals("guolili", action.getUser());
	}

	@Test
	public void testForTest2() {
		request.setParameter("user", "zhuzhen");
		ActionProxy proxy = getActionProxy("/forTest.do");
		IndexAction action = (IndexAction) proxy.getAction();
		String result = StringUtils.EMPTY;
		try {
			result = proxy.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(ActionSupport.ERROR, result);
		assertEquals("zhuzhen", action.getUser());
	}
}
