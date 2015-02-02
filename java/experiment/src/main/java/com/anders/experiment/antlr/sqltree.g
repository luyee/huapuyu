header
{
package com.vip.venus.jdbc.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vip.venus.jdbc.parser.exception.SQLParserException;
import com.vip.venus.jdbc.parser.expression.Alias;
import com.vip.venus.jdbc.parser.expression.Expression;
import com.vip.venus.jdbc.parser.expression.ExpressionList;
import com.vip.venus.jdbc.parser.expression.operators.conditional.AndExpression;
import com.vip.venus.jdbc.parser.expression.operators.conditional.OrExpression;
import com.vip.venus.jdbc.parser.expression.operators.relational.EqualsTo;
import com.vip.venus.jdbc.parser.expression.operators.relational.In;
import com.vip.venus.jdbc.parser.expression.variable.Column;
import com.vip.venus.jdbc.parser.expression.variable.Numerical;
import com.vip.venus.jdbc.parser.expression.variable.Param;
import com.vip.venus.jdbc.parser.expression.variable.QuotedString;
import com.vip.venus.jdbc.parser.statement.Statement;
import com.vip.venus.jdbc.parser.statement.delete.Delete;
import com.vip.venus.jdbc.parser.statement.insert.Insert;
import com.vip.venus.jdbc.parser.statement.select.Select;
import com.vip.venus.jdbc.parser.statement.select.from.Table;
import com.vip.venus.jdbc.parser.statement.select.select.AllColumns;
import com.vip.venus.jdbc.parser.statement.select.select.SelectExpression;
import com.vip.venus.jdbc.parser.statement.update.Update;
}

class SQLTreeParser extends TreeParser;

options
{
	importVocab=SQL;        
	exportVocab=SQLTree;    
	buildAST=true;
}

tokens
{
	FROM_FRAGMENT;	
}

{
	private Statement statement;
	private int paramIndex = 0;
	private Map<String, List<Integer>> paramIndexMap = new HashMap<String, List<Integer>>();

	public int getParamCount() {
		return this.paramIndex;
	}

	public Map<String, List<Integer>> getParamIndexMap() {
		return this.paramIndexMap;
	}

	public Statement getStatement() {
		return this.statement;
	}

	public Select getSelect() {
		if (statement instanceof Select) {
			return (Select) statement;
		}
		// FIXME Anders
		throw new SQLParserException("");
	}

	public Insert getInsert() {
		if (statement instanceof Insert) {
			return (Insert) statement;
		}
		// FIXME Anders
		throw new SQLParserException("");
	}

	public Delete getDelete() {
		if (statement instanceof Delete) {
			return (Delete) statement;
		}
		// FIXME Anders
		throw new SQLParserException("");
	}

	public Update getUpdate() {
		if (statement instanceof Update) {
			return (Update) statement;
		}
		// FIXME Anders
		throw new SQLParserException("");
	}

	public void createStatement(int statementType) {
		if (statementType == SELECT) {
			statement = new Select();
		} else if (statementType == INSERT) {
			statement = new Insert();
		} else if (statementType == DELETE) {
			statement = new Delete();
		} else if (statementType == UPDATE) {
			statement = new Update();
		} else {
			throw new SQLParserException("not support statement type : " + statementType);
		}
	}

	public SelectExpression createSelectExpression(AST column, AST alias, boolean useAs) {
		SelectExpression item = new SelectExpression();
		item.setExpression(new Column(column.getText()));
		if (alias != null) {
			item.setAlias(new Alias(alias.getText(), useAs));
		}
		return item;
	}

	public Table createTable(AST table, AST alias, boolean useAs) {
		Table item = new Table();
		item.setName(table.getText());
		if (alias != null) {
			item.setAlias(new Alias(alias.getText(), useAs));
		}
		return item;
	}

	public ExpressionList createSetList(AST setAst) {
		AST ast = setAst.getFirstChild();
		if (ast == null) {
			throw new SQLParserException("expressions is null");
		}

		ExpressionList expressionList = new ExpressionList();
		do {
			AST left = ast.getFirstChild();
			if (left == null) {
				throw new SQLParserException("left is null");
			}
			AST right = left.getNextSibling();
			if (left == null) {
				throw new SQLParserException("right is null");
			}
			expressionList.addExpression(new EqualsTo(new Column(left.getText()), new Numerical(right.getText())));
			ast = ast.getNextSibling();
		} while (ast != null);

		return expressionList;
	}

	public ExpressionList createColumnList(AST exprAst) {
		AST ast = exprAst.getFirstChild();
		if (ast == null) {
			throw new SQLParserException("expressions is null");
		}

		ExpressionList expressionList = new ExpressionList();
		do {
			expressionList.addExpression(new Column(ast.getText()));
			ast = ast.getNextSibling();
		} while (ast != null);

		return expressionList;
	}

	public ExpressionList createExpressionList(AST exprAst) {
		AST ast = exprAst.getFirstChild();
		if (ast == null) {
			throw new SQLParserException("expressions is null");
		}

		ExpressionList expressionList = new ExpressionList();
		do {
			expressionList.addExpression(new Numerical(ast.getText()));
			ast = ast.getNextSibling();
		} while (ast != null);

		return expressionList;
	}

	public In createIn(AST exprAst, int preIndex) {
		AST ast = exprAst.getFirstChild();
		if (ast == null) {
			throw new SQLParserException("column is null");
		}
		String column = ast.getText();
		List<Integer> indexes = paramIndexMap.get(column);
		if (indexes == null) {
			indexes = new ArrayList<Integer>();
			paramIndexMap.put(column, indexes);
		}

		ast = ast.getNextSibling();
		if (ast == null) {
			throw new SQLParserException("in expressions is null");
		}

		int index = preIndex;
		ExpressionList expressionList = new ExpressionList();
		do {
			// FIXME Anders Numerical need to setting right type
			expressionList.addExpression(new Numerical(ast.getText()));
			if (ast.getType() == PARAM) {
				indexes.add(++index);
			}
			ast = ast.getNextSibling();
		} while (ast != null);

		return new In(new Column(column), expressionList);
	}
}

statement
	: selectStatement | insertStatement | deleteStatement | updateStatement
	;

selectStatement
	: selectRoot
	;

selectRoot {
	Expression where = null;
	}
	: #(SELECT_ROOT { 
		createStatement(SELECT); 
	} 
		s:selectClause
		f:fromClause
		where=whereClause {getSelect().setWhere(where);}
	) {
	}
	;

selectClause
	: #(SELECT { 
	} 
		selectedList
	) {
	}
	;

selectedList
	: STAR {
		getSelect().addSelectItem(new AllColumns());
	} | (selectExpression (COMMA! selectExpression)*)
	;
	
selectExpression
	: c1:IDENT (a1:IDENT)? {
		getSelect().addSelectItem(createSelectExpression(#c1, #a1, false));
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		getSelect().addSelectItem(createSelectExpression(#c2, #a2, true));
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
		statement.setFromItem(createTable(#c1, #a1, false));
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		statement.setFromItem(createTable(#c2, #a2, true));
	}
	;
	
whereClause returns [Expression expr] {
		expr = null;
	}
	: #(WHERE expr=logicalExpression) {
	}
	;

insertStatement
	: insertRoot
	;

insertRoot
	: #(INSERT_ROOT { 
		createStatement(INSERT); 
	} 
		i:insertClause
		c:columnList {getInsert().setColumnList(createColumnList(#c));}
		v:valuesClause {getInsert().setExpressionList(createExpressionList(#v));}
	) {
	}
	;

insertClause
	: #(INSERT 
		i:IDENT) {
		statement.setFromItem(createTable(#i, null, false));
	}
	;

columnList
	: #(COLUMN_LIST column (column)*)
	;

valuesClause
	: #(VALUES variable (variable)*)
	;

deleteStatement {
		Expression where = null;
	}
	: #(DELETE_ROOT { 
		createStatement(DELETE); 
	}
		d:deleteClause
		where=whereClause {getDelete().setWhere(where);}
	)
	;

deleteClause
	: #(DELETE
		i:IDENT {
			statement.setFromItem(createTable(#i, null, false));
		}
	)
	;

updateStatement {
	Expression where = null;
	}
	: #(UPDATE_ROOT {
		createStatement(UPDATE); 
	}
		updateClause 
		s:setClause {getUpdate().setSetList(createSetList(#s));}
		where=whereClause {getUpdate().setWhere(where);}
	)
	;

updateClause
	: #(UPDATE {
	}
		fromExpression) {
	} 
	;

setClause
	: #(SET
		(equalityExpression)+ {
		}
	)
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
	}
	: expr=equalsToExpression 
	| i:inExpression {expr=createIn(#i, paramIndex);}
	;

equalsToExpression returns [Expression expr] {
		expr = null;
		Expression rr = null;
	}
	: #(EQ 
			ll:IDENT {
		}
			rr=constant {
		}) {
		expr = new EqualsTo(new Column(#ll.getText()), rr);
		if (rr instanceof Param) {
			List<Integer> indexes = paramIndexMap.get(#ll.getText());
			if (indexes == null) {
				indexes = new ArrayList<Integer>();
				paramIndexMap.put(#ll.getText(), indexes);
			}
			indexes.add(++paramIndex);
		}
	}
	;

inExpression 
	: #(IN IDENT (variable)+) {

	}
	;

constant returns [Expression value] {
		value = null;
	}
	: value=column
	| value=variable
	;

column returns [Expression value] {
		value = null;
	}
	: i:IDENT {value = new Column(#i.getText());}
	;

variable returns [Expression value] {
		value = null;
	}
	: n:NUMERICAL {value = new Numerical(#n.getText());}
	| q:QUOTED_STRING {value = new QuotedString(#q.getText());}
	| PARAM {value = new Param();++paramIndex;}
	;