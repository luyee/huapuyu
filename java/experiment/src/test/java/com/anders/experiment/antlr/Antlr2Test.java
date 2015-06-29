package com.anders.experiment.antlr;


public class Antlr2Test {

	// @Test
	// public void test4() throws RecognitionException,
	// antlr.RecognitionException, TokenStreamException {
	// String sql = "select * from tabbbb as table where id = 1";
	// SQLLexer lexer = new SQLLexer(new StringReader(sql));
	// SQLParser parser = new SQLParser(lexer);
	// parser.statement();
	//
	// AST rootAst = parser.getAST();
	// System.out.println(rootAst.getType());
	//
	// // select
	// AST selectAst = rootAst.getFirstChild();
	// System.out.println(selectAst);
	//
	// // from
	// AST fromAst = selectAst.getNextSibling();
	// System.out.println(fromAst);
	// AST asAst = fromAst.getFirstChild();
	// System.out.println(asAst);
	// // System.out.println(asAst.getNumberOfChildren());
	// System.out.println(asAst.getFirstChild());
	// System.out.println(asAst.getFirstChild().getNextSibling());
	//
	// // where
	// AST whereAst = fromAst.getNextSibling();
	// System.out.println(whereAst);
	// // System.out.println(whereAst.getNumberOfChildren());
	// System.out.println(whereAst.getFirstChild());
	// System.out.println(whereAst.getFirstChild().getNextSibling());
	// System.out.println(whereAst.getFirstChild().getNextSibling().getNextSibling());
	//
	// SQLTreeParser sqlTreeParser = new SQLTreeParser();
	// sqlTreeParser.statement(rootAst);
	// }
}
