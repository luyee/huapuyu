package com.anders.experiment.sqlparser.fundationdb;

import java.util.Date;

import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;

public class ParserTest {
	public static void main(String[] args) throws Exception {
		SQLParser parser = new SQLParser();
		Long begin = new Date().getTime();
		StatementNode stmt = parser
				.parseStatement("select userid,username,password "
						+ "from sys_user where username = 'isea533'");
		Long end = new Date().getTime();
		System.out.println("耗时：" + (end - begin));
		stmt.treePrint();
	}
}
