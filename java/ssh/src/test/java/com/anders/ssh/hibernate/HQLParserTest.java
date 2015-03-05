package com.anders.ssh.hibernate;

import java.util.HashMap;

import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import antlr.RecognitionException;
import antlr.TokenStreamException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml" })
public class HQLParserTest {

	@Autowired
	private SessionFactoryImplementor SesssessionFactory;

	@Test
	public void test1() throws RecognitionException, TokenStreamException {
		String hql = "select new Map(id as id, name as name) from Account order by id";
		QueryTranslatorImpl queryTranslatorImpl = new QueryTranslatorImpl(hql, "select new Map(id as id, name as name) from com.anders.ssh.bo.test.Account order by id", new HashMap(), SesssessionFactory);
		queryTranslatorImpl.compile(null, false);
		System.out.println("sql is : " + queryTranslatorImpl.getSQLString());

		System.out.println(queryTranslatorImpl.getReturnAliases()[0]);

		// HqlParser parser = HqlParser.getInstance(hql);
		// parser.statement();
		// AST hqlAst = parser.getAST();
		//
		// HqlSqlWalker w = new HqlSqlWalker(queryTranslatorImpl, SesssessionFactory, parser, new HashMap(), "");
		// w.statement(hqlAst);
		// System.out.println("***********************" + w.getFinalFromClause());
	}

}
