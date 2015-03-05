package com.anders.experiment.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelTest {

	@Test
	public void testSpel() {
		User user = new User();
		user.setId(1L);
		user.setName("zhuzhen");

		Collection<Long> set = new HashSet<Long>();
		set.add(4L);
		set.add(5L);

		Collection<Long> list = new ArrayList<Long>();
		list.add(6L);
		list.add(7L);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", 8);
		map.put("b", 9);

		Map<Long, Integer> map1 = new HashMap<Long, Integer>();
		map1.put(11L, 8);
		map1.put(12L, 9);

		ExpressionParser ep = new SpelExpressionParser();
		Assert.assertTrue(ep.parseExpression("true and true").getValue(
				Boolean.class));

		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("user", user);
		context.setVariable("id", 1L);
		context.setVariable("set", set);
		context.setVariable("list", list);
		context.setVariable("map", map);
		context.setVariable("map1", map1);

		System.out.println(ep.parseExpression("#id").getValue(context));
		System.out.println(ep.parseExpression("#id").getValue(context)
				.getClass());
		System.out.println(ep.parseExpression("#user.name").getValue(context));
		System.out.println(ep.parseExpression("#user.name").getValue(context)
				.getClass());
		System.out.println(ep.parseExpression("#set[0]").getValue(context));
		System.out.println(ep.parseExpression("#list").getValue(context));
		System.out.println(ep.parseExpression("#list[0]").getValue(context));
		System.out.println(ep.parseExpression("#map[a]").getValue(context));
		System.out.println(ep.parseExpression("#map1[11L]").getValue(context));

	}
}
