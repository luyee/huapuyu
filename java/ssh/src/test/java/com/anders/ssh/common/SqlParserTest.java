package com.anders.ssh.common;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

import org.junit.Test;

public class SqlParserTest {
	@Test
	public void test() throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse("SELECT * FROM MY_TABLE1 a, tableb b where a.id = b.id");
		if (statement instanceof Select) {
			System.out.println("true");
		}
		Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<SelectItem> selectItems = ((PlainSelect) selectStatement.getSelectBody()).getSelectItems();
		for (SelectItem si : selectItems) {
			System.out.println(((AllColumns) si));
		}

		System.out.println(((PlainSelect) selectStatement.getSelectBody()).getWhere());
		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		for (String table : tableList) {
			System.out.println(table);
		}

		Expression expression = ((PlainSelect) selectStatement.getSelectBody()).getWhere();
		// ((WhenClause) expression).getWhenExpression()
		System.out.println(expression.getClass());
		Expression andExpression = ((AndExpression) expression).getLeftExpression();
		System.out.println(andExpression.getClass());
		System.out.println(((EqualsTo) andExpression).getLeftExpression().getClass());
		Column column = (Column) ((EqualsTo) andExpression).getLeftExpression();
		System.out.println(column.getColumnName());
	}

	@Test
	public void test1() throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse("SELECT * FROM (select * from tbuser) a where a.id = 1");
		if (statement instanceof Select) {
			System.out.println("true");
		}
		Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		System.out.println(((PlainSelect) selectStatement.getSelectBody()).getWhere());
		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		for (String table : tableList) {
			System.out.println(table);
		}

		Expression expression = ((PlainSelect) selectStatement.getSelectBody()).getWhere();
		// ((WhenClause) expression).getWhenExpression()
		System.out.println(expression.getClass());
		Column column = (Column) ((EqualsTo) expression).getLeftExpression();
		System.out.println(column.getColumnName());
		Object value = (LongValue) ((EqualsTo) expression).getRightExpression();
		System.out.println(value);
	}

	@Test
	public void test2() throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse("SELECT * FROM tbuser a left join tt d where a.id = 1 and d.id = a.id");
		if (statement instanceof Select) {
			System.out.println("true");
		}
		Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		for (String table : tableList) {
			System.out.println(table);
		}
	}
	
	@Test
	public void test3() throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse("/* hint force */ SELECT *, currenttime() FROM tbuser where name = '中文 ' limit 10,10");
		if (statement instanceof Select) {
			System.out.println("true");
		}
		Select selectStatement = (Select) statement;
		System.out.println(selectStatement.getSelectBody());
		
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		for (String table : tableList) {
			System.out.println(table);
		}
	}
	
	@Test(expected=JSQLParserException.class)
	public void test4() throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse("INSERT INTO table1 (a,c) VALUES (1,3) ON DUPLICATE KEY UPDATE c=c+1");
	}
}
