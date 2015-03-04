header 
{
package com.vip.venus.jdbc.parser;

import com.vip.venus.jdbc.parser.exception.SQLParserException;
}

class SQLParser extends Parser;

options
{
	exportVocab=SQL;
	buildAST=true;
	k=3;  
	defaultErrorHandler=false;  
}

tokens
{
	//ALL="all";
	//ANY="any";
	AND="and";
	AS="as";
	//ASCENDING="asc";
	//AVG="avg";
	//BETWEEN="between";
	//CLASS="class";
	//COUNT="count";
	DELETE="delete";
	//DESCENDING="desc";
	//DOT;
	//DISTINCT="distinct";
	//ELEMENTS="elements";
	//ESCAPE="escape";
	//EXISTS="exists";
	//FALSE="false";
	//FETCH="fetch";
	FROM="from";
	//FULL="full";
	//GROUP="group";
	//HAVING="having";
	IN="in";
	//INDICES="indices";
	//INNER="inner";
	INSERT="insert";
	INTO="into";
	VALUES="values";
	//IS="is";
	//JOIN="join";
	//LEFT="left";
	//LIKE="like";
	//MAX="max";
	//MIN="min";
	//NEW="new";
	//NOT="not";
	//NULL="null";
	OR="or";
	//ORDER="order";
	//OUTER="outer";
	//PROPERTIES="properties";
	//RIGHT="right";
	SELECT="select";
	SET="set";
	//SOME="some";
	//SUM="sum";
	//TRUE="true";
	//UNION="union";
	UPDATE="update";
	//VERSIONED="versioned";
	WHERE="where";
	LEFT="left";
	RIGHT="right";
	INNER="inner";
	CROSS="cross";
	JOIN="join";
	ON="on";
	//NULLS="nulls";
	//FIRST;
	//LAST;

	// -- SQL tokens --
	// These aren't part of HQL, but the SQL fragment parser uses the HQL lexer, so they need to be declared here.
	//CASE="case";	// a "searched case statement", whereas CASE2 represents a "simple case statement"
	//END="end";
	//ELSE="else";
	//THEN="then";
	//WHEN="when";
	//ON="on";
	//WITH="with";

	// -- JPAQL tokens --
	//BOTH="both";
	//EMPTY="empty";
	//LEADING="leading";
	//MEMBER="member";
	//OBJECT="object";
	//OF="of";
	//TRAILING="trailing";
	//KEY;
	//VALUE;
	//ENTRY;

	// -- Synthetic token types --
	//AGGREGATE;		// One of the aggregate functions (e.g. min, max, avg)
	//ALIAS;
	//CONSTRUCTOR;
	//CASE2;			// a "simple case statement", whereas CASE represents a "searched case statement"
	//CAST;
	//EXPR_LIST;
	//FILTER_ENTITY;		// FROM element injected because of a filter expression (happens during compilation phase 2)
	//IN_LIST;
	//INDEX_OP;
	//IS_NOT_NULL;
	//IS_NULL;			// Unary 'is null' operator.
	//METHOD_CALL;
	//NOT_BETWEEN;
	//NOT_IN;
	//NOT_LIKE;
	//ORDER_ELEMENT;
	SELECT_ROOT;
	INSERT_ROOT;
	DELETE_ROOT;
	UPDATE_ROOT;
	COLUMN_LIST;
	//RANGE;
	//ROW_STAR;
	//SELECT_FROM;
	//UNARY_MINUS;
	//UNARY_PLUS;
	//VECTOR_EXPR;		// ( x, y, z )
	//WEIRD_IDENT;		// Identifiers that were keywords when they came in.

	// Literal tokens.
	//CONSTANT;
	//NUM_DOUBLE;
	//NUM_FLOAT;
	//NUM_LONG;
	//NUM_BIG_INTEGER;
	//NUM_BIG_DECIMAL;
	//JAVA_CONSTANT;
}

statement
	: (selectStatement | insertStatement | deleteStatement | updateStatement) EOF 
	exception 
	catch [Throwable e] {throw new SQLParserException(e);}
	;
	
selectStatement
	: selectRoot {
		#selectStatement = #([SELECT_ROOT,"select_root"], #selectStatement);
	}
	;
	
selectRoot
	: selectClause fromClause whereClause
	;
	
selectClause
	: SELECT^ selectedList
	;
	
selectedList
	: STAR | (aliasedExpression (COMMA aliasedExpression)*)
	;
	
aliasedExpression
	: IDENT (IDENT | (AS^ IDENT))?
	;

fromClause
	: FROM^ aliasedExpression (joinClause)* 
	;

joinClause
	: LEFT^ JOIN! aliasedExpression onClause |
	RIGHT^ JOIN! aliasedExpression onClause |
	JOIN^ aliasedExpression onClause
	;

onClause
	: ON^ logicalExpression
	;
	
whereClause
	: WHERE^ logicalExpression
	;

insertStatement
	: insertRoot {
		#insertStatement = #([INSERT_ROOT,"insert_root"], #insertStatement);
	}
	;
	
insertRoot
	: insertClause columnList valuesClause
	;

insertClause
	: INSERT^ INTO! IDENT
	;

columnList
	: OPEN! IDENT (COMMA! IDENT)* CLOSE! {
		#columnList = #([COLUMN_LIST,"column_list"], #columnList);
	}
	;

valuesClause
	: VALUES^ OPEN! variable (COMMA! variable)* CLOSE!
	;

deleteStatement
	: deleteRoot {
		#deleteStatement = #([DELETE_ROOT,"delete_root"], #deleteStatement);
	}
	;

deleteRoot
	: deleteClause whereClause
	;

deleteClause
	: DELETE^ FROM! IDENT
	;

logicalExpression
	: expression
	;

updateStatement
	: updateRoot {
		#updateStatement = #([UPDATE_ROOT,"update_root"], #updateStatement);
	}
	;

updateRoot
	: updateClause setClause whereClause
	;

updateClause
	: UPDATE^ aliasedExpression 
	;

setClause
	: SET^ equalityExpression (COMMA! equalityExpression)*
	;

expression
	: logicalOrExpression
	;

logicalOrExpression
	: logicalAndExpression (OR^ logicalAndExpression)*
	;

logicalAndExpression
	: negatedExpression (AND^ negatedExpression)*
	;

negatedExpression
	: equalityExpression 
	;

equalityExpression
	: equalsToExpression | inExpression
	;

equalsToExpression
	: IDENT EQ^ constant
	;

inExpression
	: IDENT IN^ OPEN! variable (COMMA! variable)* CLOSE!
	;

constant
	: column
	| variable
	;

column
	: IDENT
	;

variable
	: NUMERICAL
	| QUOTED_STRING
	| PARAM
	;


class SQLLexer extends Lexer;

options {
	exportVocab=SQL;   
	testLiterals = false;
	k=2; 
	charVocabulary='\u0000'..'\uFFFE';
	caseSensitive = false;
	caseSensitiveLiterals = false;
}

EQ: '=';
LT: '<';
GT: '>';
SQL_NE: "<>";
NE: "!=" | "^=";
LE: "<=";
GE: ">=";

COMMA: ',';

OPEN: '(';
CLOSE: ')';
OPEN_BRACKET: '[';
CLOSE_BRACKET: ']';

CONCAT: "||";
PLUS: '+';
MINUS: '-';
STAR: '*';
DIV: '/';
MOD: '%';
COLON: ':';
PARAM: '?';
DOT: '.';

IDENT options { testLiterals=true; }
	: ID_START_LETTER ( ID_LETTER )*
	;

protected
ID_START_LETTER
    :    '_'
    |    '$'
    |    'a'..'z'
    |    '\u0080'..'\ufffe'       
    ;

protected
ID_LETTER
    :    ID_START_LETTER
    |    '0'..'9'
    |	 DOT // FIXME Anders need to edit
    ;

QUOTED_STRING
	:	 '\'' ( (ESCqs)=> ESCqs | ~'\'' )* '\''
	;

NUMERICAL
	:	 ('1'..'9') ('0'..'9')*
	;

protected
ESCqs
	:
		'\'' '\''
	;

WS  :   (   ' '
		|   '\t'
		|   '\r' '\n' { newline(); }
		|   '\n'      { newline(); }
		|   '\r'      { newline(); }
		)
		{$setType(Token.SKIP);} 
	;