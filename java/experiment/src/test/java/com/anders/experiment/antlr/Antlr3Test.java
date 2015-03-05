package com.anders.experiment.antlr;

import org.antlr.runtime.RecognitionException;

public class Antlr3Test {
	public void test1() throws RecognitionException {
		// ANTLRStringStream input;
		// ParserLexer lexer;
		// CommonTokenStream tokens;
		// ParserParser parser;
		//
		// String sql = "SELECT max(price) AS maxprice, max(time) AS currenttime FROM Packet[Dimension1] INTO Aggregate";
		//
		// input = new ANTLRStringStream(sql);
		// lexer = new ParserLexer(input);
		// tokens = new CommonTokenStream(lexer);
		// parser = new ParserParser(tokens);
		//
		// statement_return sReturn = parser.statement();
		// CommonTree commonTree = sReturn.tree;
		// Tree tree = commonTree.getChild(0);
		// int treeType = tree.getType();
		// System.out.println(treeType);
		// }
		//
		// public void test2() throws RecognitionException {
		// ANTLRStringStream input;
		// MySQLParser parser;
		// MySQLLexer lexer;
		// CommonTokenStream token;
		//
		// String sql = "select * from (select * from tb) a where id = 1 and name = 'zhuzhen'";
		//
		// input = new ANTLRStringStream(sql);
		// lexer = new MySQLLexer(input);
		// token = new CommonTokenStream(lexer);
		// parser = new MySQLParser(token);
		//
		// select_expression_return statement_return = parser.select_expression();
		//
		//
		// CommonTree commonTree = (CommonTree) statement_return.tree;
		// System.out.println(commonTree.getType());
		//
		// for (int i = 0; i < commonTree.getChildCount(); i++) {
		// Tree tree = commonTree.getChild(i);
		// System.out.println(tree.getText());
		// System.out.println(tree.getType());
		// }
		//
		// // select_statement_return statement_return = parser.select_statement();
		// // CommonTree commonTree = (CommonTree) statement_return.tree;
		// //
		// // for (int i = 0; i < commonTree.getChildCount(); i++) {
		// // Tree tree = commonTree.getChild(i);
		// // System.out.println(tree.getText());
		// // }
		//
		// }
		//
		// public void test3() throws RecognitionException {
		// ANTLRStringStream input;
		// MySQLParser parser;
		// MySQLLexer lexer;
		// CommonTokenStream token;
		//
		// String sql = "select * from (select * from tbuser) a where a.id = 1";
		//
		// input = new ANTLRStringStream(sql);
		// lexer = new MySQLLexer(input);
		// token = new CommonTokenStream(lexer);
		// parser = new MySQLParser(token);
		//
		// select_expression_return statement_return = parser.select_expression();
		// // select_statement_return statement_return = parser.select_statement();
		// System.out.println(parser.getTreeAdaptor().getClass());
		// CommonTreeAdaptor treeAdaptor = (CommonTreeAdaptor) parser.getTreeAdaptor();
		// System.out.println(treeAdaptor.getChildCount(statement_return.tree));
		//
		// ASTPair asttt;
		//
		// CommonTree commonTree = (CommonTree) statement_return.tree;
		// System.out.println(commonTree.getType());
		//
		// for (int i = 0; i < commonTree.getChildCount(); i++) {
		// Tree tree = commonTree.getChild(i);
		// // System.out.println(tree.getType()+"\t"+tree.getText());
		// switch (tree.getType()) {
		// case 581:
		// input = new ANTLRStringStream(sql);
		// lexer = new MySQLLexer(input);
		// token = new CommonTokenStream(lexer);
		// parser = new MySQLParser(token);
		// select_statement_return sr = parser.select_statement();
		// CommonTree ct = (CommonTree) sr.tree;
		// for (int j = 0; j < ct.getChildCount(); j++) {
		// Tree t = commonTree.getChild(j);
		// System.out.println(t.getType()+"\t"+t.getText());
		// }
		// break;
		//
		// default:
		// break;
		// }
		// }
		//
		//
		// }
		//
		//
		// @Test
		// public void test4() throws RecognitionException {
		// ANTLRStringStream input;
		// MySQLParser parser;
		// MySQLLexer lexer;
		// CommonTokenStream token;
		//
		// String sql = "select * from (select * from tbuser) a where a.id = 1";
		//
		// input = new ANTLRStringStream(sql);
		// lexer = new MySQLLexer(input);
		// token = new CommonTokenStream(lexer);
		// parser = new MySQLParser(token);
		//
		// root_statement_return statement_return = parser.root_statement();
		// // select_statement_return statement_return = parser.select_statement();
		//
		// // CommonTreeNodeStream treeStream = new CommonTreeNodeStream(statement_return.getTree());
		// CommonTree commonTree = (CommonTree) statement_return.tree;
		// for (int i = 0; i < commonTree.getChildCount(); i++) {
		// CommonTree subtree = (CommonTree) commonTree.getChild(i);
		// System.out.println(subtree.getType()+"\t"+subtree.getText());
		// }
	}
}
