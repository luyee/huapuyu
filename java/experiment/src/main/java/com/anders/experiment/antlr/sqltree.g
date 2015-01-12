header
{
package com.vip.venus.jdbc.parser;

import com.vip.venus.jdbc.parser.expression.Alias;
import com.vip.venus.jdbc.parser.expression.Column;
import com.vip.venus.jdbc.parser.expression.Expression;
import com.vip.venus.jdbc.parser.expression.Numerical;
import com.vip.venus.jdbc.parser.expression.Param;
import com.vip.venus.jdbc.parser.expression.QuotedString;
import com.vip.venus.jdbc.parser.expression.operators.conditional.AndExpression;
import com.vip.venus.jdbc.parser.expression.operators.conditional.OrExpression;
import com.vip.venus.jdbc.parser.expression.operators.relational.EqualsTo;
import com.vip.venus.jdbc.parser.statement.Statement;
import com.vip.venus.jdbc.parser.statement.select.Select;
import com.vip.venus.jdbc.parser.statement.select.from.Table;
import com.vip.venus.jdbc.parser.statement.select.select.AllColumns;
import com.vip.venus.jdbc.parser.statement.select.select.SelectExpression;
import com.vip.venus.jdbc.parser.statement.select.select.SelectItem;
}

class SQLTreeParser extends TreeParser;

options
{
	// Note: importVocab and exportVocab cause ANTLR to share the token type numbers between the
	// two grammars.  This means that the token type constants from the source tree are the same
	// as those in the target tree.  If this is not the case, tree translation can result in
	// token types from the *source* tree being present in the target tree.
	importVocab=SQL;        // import definitions from "Hql"
	exportVocab=SQLTree;     // Call the resulting definitions "HqlSql"
	buildAST=true;
}

tokens
{
	FROM_FRAGMENT;	// A fragment of SQL that represents a table reference in a FROM clause.
	//IMPLIED_FROM;	// An implied FROM element.
	//JOIN_FRAGMENT;	// A JOIN fragment.
	//SELECT_CLAUSE;
	//LEFT_OUTER;
	//RIGHT_OUTER;
	//ALIAS_REF;      // An IDENT that is a reference to an entity via it's alias.
	//PROPERTY_REF;   // A DOT that is a reference to a property in an entity.
	//SQL_TOKEN;      // A chunk of SQL that is 'rendered' already.
	//SELECT_COLUMNS; // A chunk of SQL representing a bunch of select columns.
	//SELECT_EXPR;    // A select expression, generated from a FROM element.
	//THETA_JOINS;	// Root of theta join condition subtree.
	//FILTERS;		// Root of the filters condition subtree.
	//METHOD_NAME;    // An IDENT that is a method name.
	//NAMED_PARAM;    // A named parameter (:foo).
	//BOGUS;          // Used for error state detection, etc.
	//RESULT_VARIABLE_REF;   // An IDENT that refers to result variable
	                       // (i.e, an alias for a select expression) 
}

// -- Declarations --
{
	private Statement statement;

	public Statement getStatement() {
		return this.statement;
	}

	public Select getSelect() {
		if (statement instanceof Select) {
			return (Select) statement;
		}
		// FIXME Anders add exception msg
		throw new RuntimeException();
	}

	public void createStatement(int statementType) {
		if (statementType == SELECT) {
			statement = new Select();
		} else if (statementType == INSERT) {
			//statement = new Insert();
		} else if (statementType == DELETE) {
			//statement = new Delete();
		} else if (statementType == UPDATE) {
			//statement = new Update();
		} else {
			// FIXME Anders add exception msg
			throw new RuntimeException();
		}
	}

	public void createAllColumns() {
		SelectItem item = new AllColumns();
		getSelect().addSelectItem(item);
	}

	public void createSelectExpression(AST column, AST alias, boolean useAs) {
		SelectExpression item = new SelectExpression();
		item.setExpression(new Column(column.getText()));
		if (alias != null) {
			item.setAlias(new Alias(alias.getText(), useAs));
		}
		getSelect().addSelectItem(item);
	}

	public void createTable(AST table, AST alias, boolean useAs) {
		Table item = new Table();
		item.setName(table.getText());
		String aliasName = (alias == null) ? null : alias.getText();
		item.setAlias(new Alias(aliasName, useAs));
		getSelect().setFromItem(item);
	}

	public void setWhere(Expression where) {
		getSelect().setWhere(where);
	}
}

// The main statement rule.
statement
	: selectStatement 
	;

selectStatement
	: selectRoot
	;

selectRoot
	: #(ROOT { 
		createStatement(SELECT); 
	} 
		s:selectClause
		f:fromClause
		(w:whereClause)?
	) {
		// Antlr note: #x_in refers to the input AST, #x refers to the output AST
		//#selectRoot = #([ROOT,"root"], #s);
		//beforeStatementCompletion( "select" );
		//processQuery( #s, #query );
		//afterStatementCompletion( "select" );
	}
	;

selectClause
	: #(SELECT { 
			//handleClauseStart( SELECT ); 
			//beforeSelectClause(); 
	} 
		x:selectedList
	) {
		//#selectClause = #([SELECT_CLAUSE,"{select clause}"], #x);
			//System.out.println(#x);
	}
	;

selectedList
	: STAR {
		createAllColumns();
	} | (selectExpression (COMMA! selectExpression)*)
	;
	
selectExpression
	: c1:IDENT (a1:IDENT)? {
		createSelectExpression(#c1, #a1, false);
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		createSelectExpression(#c2, #a2, true);
	}
	;
	
fromClause
	: #(FROM {
	}
	fromExpression) {
	} 
	;

fromExpression
	: c1:IDENT (a1:IDENT)? {
		createTable(#c1, #a1, false);
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		createTable(#c2, #a2, true);
	}
	;
	
whereClause {
		Expression expr = null;
	}
	: #(WHERE expr=logicalExpression) {
		setWhere(expr);
	}
	;

logicalExpression returns [Expression expr] {
		expr = null;
	}
	: expr=expression
	;

expression returns [Expression expr] {
		expr = null;
	}
	: expr=logicalOrExpression
	;

logicalOrExpression returns [Expression expr] {
		expr = null;
		Expression l = null;
		Expression r = null;
	}
	: expr=logicalAndExpression {
	} | #(OR 
			l=logicalAndExpression
			r=logicalAndExpression
		) {
		expr = new OrExpression(l, r);
	}
	;

logicalAndExpression returns [Expression expr] {
		expr = null;
		Expression l = null;
		Expression r = null;
	}
	: expr=negatedExpression {
	} | #(AND
			l=negatedExpression 
			r=negatedExpression
		) {
		expr = new AndExpression(l, r);
	}
	;

negatedExpression returns [Expression expr] {
		expr = null;
	}
	: expr=equalityExpression {
	}
	;

equalityExpression returns [Expression expr] {
		expr = null;
		Expression rr = null;
	}
	: #(EQ 
			ll:IDENT {
		}
			rr=constant {
		}) {
		expr = new EqualsTo(new Column(#ll.getText()), rr);
	}
	;

constant returns [Expression value] {
		value = null;
	}
	: i:IDENT {value = new Column(#i.getText());}
	| n:NUMERICAL {value = new Numerical(#n.getText());}
	| q:QUOTED_STRING {value = new QuotedString(#q.getText());}
	| PARAM {value = new Param();}
	;