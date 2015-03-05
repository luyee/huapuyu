package javacc;

import java.io.StringReader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class JavaCCTester {

	public static void main(String[] args) throws JSQLParserException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		String statement = "SELECT * FROM mytable WHERE mytable.col = 9 LIMIT 3, ?";

		Select select = (Select) parserManager.parse(new StringReader(statement));

		// System.out.println(((PlainSelect) select.getSelectBody()).getLimit().getOffset());
		// System.out.println(((PlainSelect) select.getSelectBody()).getLimit().isRowCountJdbcParameter());
		// System.out.println(((PlainSelect) select.getSelectBody()).getLimit().isOffsetJdbcParameter());
		// System.out.println(((PlainSelect) select.getSelectBody()).getLimit().isLimitAll());
		System.out.println(((PlainSelect) select.getSelectBody()).getFromItem());
		System.out.println(((PlainSelect) select.getSelectBody()).getWhere());
	}

}
