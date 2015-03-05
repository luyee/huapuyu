// $ANTLR 2.7.7 (20060906): "sql.g" -> "SQLParser.java"$

package com.anders.experiment.antlr;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class SQLParser extends antlr.LLkParser       implements SQLTokenTypes
 {

protected SQLParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SQLParser(TokenBuffer tokenBuf) {
  this(tokenBuf,3);
}

protected SQLParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SQLParser(TokenStream lexer) {
  this(lexer,3);
}

public SQLParser(ParserSharedInputState state) {
  super(state,3);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		
		try {      // for error handling
			selectStatement();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = statement_AST;
	}
	
	public final void selectStatement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectStatement_AST = null;
		
		try {      // for error handling
			selectRoot();
			astFactory.addASTChild(currentAST, returnAST);
			selectStatement_AST = (AST)currentAST.root;
			
					selectStatement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ROOT,"root")).add(selectStatement_AST));
				
			currentAST.root = selectStatement_AST;
			currentAST.child = selectStatement_AST!=null &&selectStatement_AST.getFirstChild()!=null ?
				selectStatement_AST.getFirstChild() : selectStatement_AST;
			currentAST.advanceChildToEnd();
			selectStatement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = selectStatement_AST;
	}
	
	public final void selectRoot() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectRoot_AST = null;
		
		try {      // for error handling
			selectClause();
			astFactory.addASTChild(currentAST, returnAST);
			fromClause();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case WHERE:
			{
				whereClause();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			selectRoot_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = selectRoot_AST;
	}
	
	public final void selectClause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectClause_AST = null;
		
		try {      // for error handling
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp1_AST);
			match(SELECT);
			selectedList();
			astFactory.addASTChild(currentAST, returnAST);
			selectClause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = selectClause_AST;
	}
	
	public final void fromClause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fromClause_AST = null;
		
		try {      // for error handling
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp2_AST);
			match(FROM);
			aliasedExpression();
			astFactory.addASTChild(currentAST, returnAST);
			fromClause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		returnAST = fromClause_AST;
	}
	
	public final void whereClause() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST whereClause_AST = null;
		
		try {      // for error handling
			AST tmp3_AST = null;
			tmp3_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp3_AST);
			match(WHERE);
			AST tmp4_AST = null;
			tmp4_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp4_AST);
			match(IDENT);
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(EQ);
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(NUM_INT);
			whereClause_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = whereClause_AST;
	}
	
	public final void selectedList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectedList_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STAR:
			{
				AST tmp7_AST = null;
				tmp7_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp7_AST);
				match(STAR);
				selectedList_AST = (AST)currentAST.root;
				break;
			}
			case IDENT:
			{
				{
				aliasedExpression();
				astFactory.addASTChild(currentAST, returnAST);
				{
				_loop9:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						aliasedExpression();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop9;
					}
					
				} while (true);
				}
				}
				selectedList_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = selectedList_AST;
	}
	
	public final void aliasedExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aliasedExpression_AST = null;
		
		try {      // for error handling
			AST tmp9_AST = null;
			tmp9_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp9_AST);
			match(IDENT);
			{
			switch ( LA(1)) {
			case AS:
			{
				AST tmp10_AST = null;
				tmp10_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp10_AST);
				match(AS);
				AST tmp11_AST = null;
				tmp11_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp11_AST);
				match(IDENT);
				break;
			}
			case EOF:
			case FROM:
			case WHERE:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			aliasedExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
		returnAST = aliasedExpression_AST;
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
		"FLOAT_SUFFIX"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 16386L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 4210818L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	
	}
