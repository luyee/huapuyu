// $ANTLR 2.7.7 (20060906): "sqltree.g" -> "SQLTreeParser.java"$

package com.anders.experiment.antlr;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class SQLTreeParser extends antlr.TreeParser       implements SQLTreeTokenTypes
 {

	private int level = 0;

	
public SQLTreeParser() {
	tokenNames = _tokenNames;
}

	public final void statement(AST _t) throws RecognitionException {
		
		AST statement_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		
		try {      // for error handling
			selectStatement(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = statement_AST;
		_retTree = _t;
	}
	
	public final void selectStatement(AST _t) throws RecognitionException {
		
		AST selectStatement_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectStatement_AST = null;
		
		try {      // for error handling
			selectRoot(_t);
			_t = _retTree;
			astFactory.addASTChild(currentAST, returnAST);
			selectStatement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = selectStatement_AST;
		_retTree = _t;
	}
	
	public final void selectRoot(AST _t) throws RecognitionException {
		
		AST selectRoot_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectRoot_AST = null;
		AST s_AST = null;
		AST s = null;
		
		try {      // for error handling
			AST __t4 = _t;
			AST tmp1_AST = null;
			AST tmp1_AST_in = null;
			tmp1_AST = astFactory.create((AST)_t);
			tmp1_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp1_AST);
			ASTPair __currentAST4 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,ROOT);
			_t = _t.getFirstChild();
			s = _t==ASTNULL ? null : (AST)_t;
			selectClause(_t);
			_t = _retTree;
			s_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST4;
			_t = __t4;
			_t = _t.getNextSibling();
			
					// Antlr note: #x_in refers to the input AST, #x refers to the output AST
					//#selectRoot = #([ROOT,"root"], #s);
					//beforeStatementCompletion( "select" );
					//processQuery( #s, #query );
					//afterStatementCompletion( "select" );
				
			selectRoot_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = selectRoot_AST;
		_retTree = _t;
	}
	
	public final void selectClause(AST _t) throws RecognitionException {
		
		AST selectClause_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectClause_AST = null;
		AST x_AST = null;
		AST x = null;
		
		try {      // for error handling
			AST __t6 = _t;
			AST tmp2_AST = null;
			AST tmp2_AST_in = null;
			tmp2_AST = astFactory.create((AST)_t);
			tmp2_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp2_AST);
			ASTPair __currentAST6 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,SELECT);
			_t = _t.getFirstChild();
			
						//handleClauseStart( SELECT ); 
						//beforeSelectClause(); 
					
			x = _t==ASTNULL ? null : (AST)_t;
			selectedList(_t);
			_t = _retTree;
			x_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			currentAST = __currentAST6;
			_t = __t6;
			_t = _t.getNextSibling();
			
					//#selectClause = #([SELECT_CLAUSE,"{select clause}"], #x);
						System.out.println(x_AST);
					
			selectClause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = selectClause_AST;
		_retTree = _t;
	}
	
	public final void selectedList(AST _t) throws RecognitionException {
		
		AST selectedList_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectedList_AST = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case STAR:
			{
				AST tmp3_AST = null;
				AST tmp3_AST_in = null;
				tmp3_AST = astFactory.create((AST)_t);
				tmp3_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp3_AST);
				match(_t,STAR);
				_t = _t.getNextSibling();
				selectedList_AST = (AST)currentAST.root;
				break;
			}
			case AS:
			{
				{
				aliasedExpression(_t);
				_t = _retTree;
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop10:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==COMMA)) {
						AST tmp4_AST_in = null;
						match(_t,COMMA);
						_t = _t.getNextSibling();
						aliasedExpression(_t);
						_t = _retTree;
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop10;
					}
					
				} while (true);
				}
				}
				selectedList_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = selectedList_AST;
		_retTree = _t;
	}
	
	public final void aliasedExpression(AST _t) throws RecognitionException {
		
		AST aliasedExpression_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aliasedExpression_AST = null;
		
		try {      // for error handling
			AST __t12 = _t;
			AST tmp5_AST = null;
			AST tmp5_AST_in = null;
			tmp5_AST = astFactory.create((AST)_t);
			tmp5_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp5_AST);
			ASTPair __currentAST12 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,AS);
			_t = _t.getFirstChild();
			AST tmp6_AST = null;
			AST tmp6_AST_in = null;
			tmp6_AST = astFactory.create((AST)_t);
			tmp6_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IDENT:
			{
				AST tmp7_AST = null;
				AST tmp7_AST_in = null;
				tmp7_AST = astFactory.create((AST)_t);
				tmp7_AST_in = (AST)_t;
				astFactory.addASTChild(currentAST, tmp7_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			currentAST = __currentAST12;
			_t = __t12;
			_t = _t.getNextSibling();
			aliasedExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = aliasedExpression_AST;
		_retTree = _t;
	}
	
	public final void fromClause1(AST _t) throws RecognitionException {
		
		AST fromClause1_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fromClause1_AST = null;
		AST a_AST = null;
		AST a = null;
		
		try {      // for error handling
			AST tmp8_AST = null;
			AST tmp8_AST_in = null;
			tmp8_AST = astFactory.create((AST)_t);
			tmp8_AST_in = (AST)_t;
			astFactory.makeASTRoot(currentAST, tmp8_AST);
			match(_t,FROM);
			_t = _t.getNextSibling();
			a = _t==ASTNULL ? null : (AST)_t;
			aliasedExpression(_t);
			_t = _retTree;
			a_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			
					System.out.println(a_AST);
				
			fromClause1_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = fromClause1_AST;
		_retTree = _t;
	}
	
	public final void whereClause1(AST _t) throws RecognitionException {
		
		AST whereClause1_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST whereClause1_AST = null;
		
		try {      // for error handling
			AST tmp9_AST = null;
			AST tmp9_AST_in = null;
			tmp9_AST = astFactory.create((AST)_t);
			tmp9_AST_in = (AST)_t;
			astFactory.makeASTRoot(currentAST, tmp9_AST);
			match(_t,WHERE);
			_t = _t.getNextSibling();
			AST tmp10_AST = null;
			AST tmp10_AST_in = null;
			tmp10_AST = astFactory.create((AST)_t);
			tmp10_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp10_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			AST tmp11_AST = null;
			AST tmp11_AST_in = null;
			tmp11_AST = astFactory.create((AST)_t);
			tmp11_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp11_AST);
			match(_t,EQ);
			_t = _t.getNextSibling();
			AST tmp12_AST = null;
			AST tmp12_AST_in = null;
			tmp12_AST = astFactory.create((AST)_t);
			tmp12_AST_in = (AST)_t;
			astFactory.addASTChild(currentAST, tmp12_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			whereClause1_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		returnAST = whereClause1_AST;
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"as\"",
		"\"delete\"",
		"DOT",
		"\"from\"",
		"\"in\"",
		"\"insert\"",
		"\"into\"",
		"\"select\"",
		"\"set\"",
		"\"update\"",
		"\"where\"",
		"ROOT",
		"NUM_DOUBLE",
		"NUM_FLOAT",
		"NUM_LONG",
		"NUM_BIG_INTEGER",
		"NUM_BIG_DECIMAL",
		"STAR",
		"COMMA",
		"IDENT",
		"EQ",
		"NUM_INT",
		"LT",
		"GT",
		"SQL_NE",
		"NE",
		"LE",
		"GE",
		"OPEN",
		"CLOSE",
		"OPEN_BRACKET",
		"CLOSE_BRACKET",
		"CONCAT",
		"PLUS",
		"MINUS",
		"DIV",
		"MOD",
		"COLON",
		"PARAM",
		"ID_START_LETTER",
		"ID_LETTER",
		"QUOTED_STRING",
		"ESCqs",
		"WS",
		"HEX_DIGIT",
		"EXPONENT",
		"FLOAT_SUFFIX",
		"FROM_FRAGMENT",
		"SELECT_CLAUSE",
		"NAMED_PARAM"
	};
	
	}
	
