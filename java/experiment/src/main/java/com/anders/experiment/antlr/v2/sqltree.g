header
{
package com.anders.zhu.jdbc.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.anders.zhu.jdbc.parser.common.HintType;
import com.anders.zhu.jdbc.parser.common.JoinType;
import com.anders.zhu.jdbc.parser.common.OrderByType;
import com.anders.zhu.jdbc.parser.exception.SQLParserException;
import com.anders.zhu.jdbc.parser.expression.Alias;
import com.anders.zhu.jdbc.parser.expression.Expression;
import com.anders.zhu.jdbc.parser.expression.ExpressionList;
import com.anders.zhu.jdbc.parser.expression.OrderByExpr;
import com.anders.zhu.jdbc.parser.expression.function.AvgFunc;
import com.anders.zhu.jdbc.parser.expression.function.CountFunc;
import com.anders.zhu.jdbc.parser.expression.function.Function;
import com.anders.zhu.jdbc.parser.expression.function.MaxFunc;
import com.anders.zhu.jdbc.parser.expression.function.MinFunc;
import com.anders.zhu.jdbc.parser.expression.function.SumFunc;
import com.anders.zhu.jdbc.parser.expression.operators.conditional.AndExpression;
import com.anders.zhu.jdbc.parser.expression.operators.conditional.OrExpression;
import com.anders.zhu.jdbc.parser.expression.operators.relational.EqualsTo;
import com.anders.zhu.jdbc.parser.expression.operators.relational.In;
import com.anders.zhu.jdbc.parser.expression.variable.Column;
import com.anders.zhu.jdbc.parser.expression.variable.Numerical;
import com.anders.zhu.jdbc.parser.expression.variable.Param;
import com.anders.zhu.jdbc.parser.expression.variable.QuotedString;
import com.anders.zhu.jdbc.parser.expression.variable.RealNumber;
import com.anders.zhu.jdbc.parser.expression.variable.Variable;
import com.anders.zhu.jdbc.parser.statement.SelectFragment;
import com.anders.zhu.jdbc.parser.statement.Statement;
import com.anders.zhu.jdbc.parser.statement.delete.Delete;
import com.anders.zhu.jdbc.parser.statement.insert.Insert;
import com.anders.zhu.jdbc.parser.statement.select.Join;
import com.anders.zhu.jdbc.parser.statement.select.Select;
import com.anders.zhu.jdbc.parser.statement.select.from.FromItem;
import com.anders.zhu.jdbc.parser.statement.select.from.SubQuery;
import com.anders.zhu.jdbc.parser.statement.select.from.Table;
import com.anders.zhu.jdbc.parser.statement.select.select.AllColumns;
import com.anders.zhu.jdbc.parser.statement.select.select.SelectExpression;
import com.anders.zhu.jdbc.parser.statement.update.Update;
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
	private Stack<SelectFragment> selectFragmentStack = new Stack<SelectFragment>();
	private Map<String, List<Integer>> paramIndexMap = new HashMap<String, List<Integer>>();
	private Map<String, List<String>> paramValueMap = new HashMap<String, List<String>>();
	private Map<String, Table> tableMap = new HashMap<String, Table>();
	private Map<String, SubQuery> subQueryMap = new HashMap<String, SubQuery>();
	private Map<Column, Column> columnEqualMap = new HashMap<Column, Column>();
	private Set<HintType> hintTypes = new HashSet<HintType>();

	public SelectFragment getCurrentSelectFragment() {
		if (selectFragmentStack.size() <= 0) {
			return null;
		}
		return selectFragmentStack.lastElement();
	}

	public SelectFragment getFirstSelectFragment() {
		if (selectFragmentStack.size() <= 0) {
			return null;
		}
		return selectFragmentStack.firstElement();
	}

	public SubQuery popSelectFragmentStack(Alias alias) {
		SubQuery subQuery = (SubQuery) selectFragmentStack.pop();
		subQueryMap.put(alias.getName(), subQuery);
		return subQuery;
	}

	public int getParamCount() {
		return this.paramIndex;
	}

	public Map<String, List<Integer>> getParamIndexMap() {
		return this.paramIndexMap;
	}

	public Map<String, List<String>> getParamValueMap() {
		return this.paramValueMap;
	}

	public Map<Column, Column> getColumnEqualMap() {
		return this.columnEqualMap;
	}

	public Statement getStatement() {
		return this.statement;
	}

	public Select getSelect() {
		if (statement instanceof Select) {
			return (Select) statement;
		}
		throw new UnsupportedOperationException("only support select statement");
	}

	public Insert getInsert() {
		if (statement instanceof Insert) {
			return (Insert) statement;
		}
		throw new UnsupportedOperationException("only support insert statement");
	}

	public Delete getDelete() {
		if (statement instanceof Delete) {
			return (Delete) statement;
		}
		throw new UnsupportedOperationException("only support delete statement");
	}

	public Update getUpdate() {
		if (statement instanceof Update) {
			return (Update) statement;
		}
		throw new UnsupportedOperationException("only support update statement");
	}

	public void createStatement(int statementType) {
		if (statementType == SELECT) {
			SelectFragment selectFragment = getCurrentSelectFragment();
			if (selectFragment != null) {
				SubQuery subQuery = new SubQuery();
				((SelectFragment) selectFragmentStack.lastElement()).setFromItem(subQuery);
				selectFragmentStack.push(subQuery);
				return;
			}
			statement = new Select();
			selectFragmentStack.push((SelectFragment) statement);
		} else if (statementType == INSERT) {
			statement = new Insert();
		} else if (statementType == DELETE) {
			statement = new Delete();
		} else if (statementType == UPDATE) {
			statement = new Update();
		} else {
			throw new SQLParserException("not support statement type : " + statementType);
		}
		statement.setHintTypes(hintTypes);
	}

	public SelectExpression createSelectExpression(AST column, AST alias, boolean useAs) {
		SelectExpression item = new SelectExpression();
		item.setExpression(createColumn(column.getText()));
		if (alias != null) {
			item.setAlias(new Alias(alias.getText(), useAs));
		}
		return item;
	}

	public void addFunction(Function function, Alias alias) {
		SelectExpression item = new SelectExpression();
		item.setExpression(function);
		if (alias != null) {
			item.setAlias(alias);
		}
		getCurrentSelectFragment().addSelectItem(item);
	}

	public Table createTable(AST table, AST alias, boolean useAs) {
		Table item = new Table();
		item.setName(table.getText());
		if (alias != null) {
			item.setAlias(new Alias(alias.getText(), useAs));
			tableMap.put(alias.getText(), item);
		}
		tableMap.put(table.getText(), item);
		
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
			if (right == null) {
				throw new SQLParserException("right is null");
			}

			Variable variable = null;
			if (right.getType() == PARAM) {
				variable = new Param();
			} else if (right.getType() == REAL_NUMBER) {
				variable = new RealNumber(right.getText());
			} else if (right.getType() == NUMERICAL) {
				variable = new Numerical(right.getText());
			} else if (right.getType() == QUOTED_STRING) {
				variable = new QuotedString(right.getText());
			} else {
				throw new UnsupportedOperationException();
			}

			expressionList.addExpression(new EqualsTo(createColumn(left.getText()), variable));
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
			expressionList.addExpression(createColumn(ast.getText()));
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
			Variable variable = null;
			if (ast.getType() == PARAM) {
				variable = new Param();
			} else if (ast.getType() == REAL_NUMBER) {
				variable = new RealNumber(ast.getText());
			} else if (ast.getType() == NUMERICAL) {
				variable = new Numerical(ast.getText());
			} else if (ast.getType() == QUOTED_STRING) {
				variable = new QuotedString(ast.getText());
			} else {
				throw new UnsupportedOperationException();
			}
			expressionList.addExpression(variable);
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
		List<String> values = paramValueMap.get(column);
		if (values == null) {
			values = new ArrayList<String>();
			paramValueMap.put(column, values);
		}

		ast = ast.getNextSibling();
		if (ast == null) {
			throw new SQLParserException("in expressions is null");
		}

		int index = preIndex;
		ExpressionList expressionList = new ExpressionList();
		do {
			if (ast.getType() == PARAM) {
				expressionList.addExpression(new Param());
				indexes.add(++index);
			} else if (ast.getType() == REAL_NUMBER) {
				expressionList.addExpression(new RealNumber(ast.getText()));
				values.add(ast.getText());
			} else if (ast.getType() == NUMERICAL) {
				expressionList.addExpression(new Numerical(ast.getText()));
				values.add(ast.getText());
			} else if (ast.getType() == QUOTED_STRING) {
				expressionList.addExpression(new QuotedString(ast.getText()));
				values.add(ast.getText());
			} else {
				throw new UnsupportedOperationException();
			}
			ast = ast.getNextSibling();
		} while (ast != null);

		return new In(createColumn(column), expressionList);
	}

	public Column createColumn(String text) {
		//if (text.contains(".")) {
		//	String[] str = text.split(".");
		//	return new Column(str[0], str[1]);
		//} 
		//return new Column(text);
		return new Column(tableMap, subQueryMap, text);
	}
}

statement
	: (hintStatement)? (selectStatement | insertStatement | deleteStatement | updateStatement)
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
		where=whereClause {getCurrentSelectFragment().setWhere(where);}
		(orderByClause)?
		(limitClause)?
	) {
	}
	;

hintStatement
	: #(HINT 
		(readWrite)? {
		} 
		(executeWay)? {
		})
	;

readWrite
	: FORCE_READ {
		hintTypes.add(HintType.FORCE_READ);
	}
	;

executeWay
	: PARALLEL_EXECUTE {
		hintTypes.add(HintType.PARALLEL_EXECUTE);
	}
	;

selectClause
	: #(SELECT { 
	} 
		selectList
	) {
	}
	;

selectList
	: (STAR {
		getCurrentSelectFragment().addSelectItem(new AllColumns());
	} | selectExpression) (COMMA! selectExpression)*
	;

selectExpression
	: selectExpr |
	maxFunc |
	minFunc | 
	sumFunc |
	avgFunc |
	countFunc
	;

maxFunc {
	Alias alias = null;
	}
	: #(MAX id:IDENT (alias=aliasedSuffix)?) {
		Column column = createColumn(#id.getText());
		addFunction(new MaxFunc(column), alias);
	}
	;

minFunc {
	Alias alias = null;
	}
	: #(MIN id:IDENT (alias=aliasedSuffix)?) {
		Column column = createColumn(#id.getText());
		addFunction(new MinFunc(column), alias);
	}
	;

sumFunc {
	Alias alias = null;
	}
	: #(SUM id:IDENT (alias=aliasedSuffix)?) {
		Column column = createColumn(#id.getText());
		addFunction(new SumFunc(column), alias);
	}
	;

avgFunc {
	Alias alias = null;
	}
	: #(AVG id:IDENT (alias=aliasedSuffix)?) {
		Column column = createColumn(#id.getText());
		addFunction(new AvgFunc(column), alias);
	}
	;

countFunc {
	Alias alias = null;
	}
	: #(COUNT 
		((id:IDENT (alias=aliasedSuffix)?) {
		Column column = createColumn(#id.getText());
		addFunction(new CountFunc(column), alias);
	} | 
	(STAR (alias=aliasedSuffix)?) {
		addFunction(new CountFunc(), alias);
	}))
	;

aliasedSuffix returns [Alias alias] {
		alias = null;
	}
	: i1:IDENT {
		alias = new Alias(i1.getText(), false);
	} |
	#(AS i2:IDENT) {
		alias = new Alias(i2.getText(), true);
	}
	;

selectExpr
	: c1:IDENT (a1:IDENT)? {
		getCurrentSelectFragment().addSelectItem(createSelectExpression(#c1, #a1, false));
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		getCurrentSelectFragment().addSelectItem(createSelectExpression(#c2, #a2, true));
	}
	;
	
fromClause
	: #(FROM
	selectFromExpression (joinClause)*)
	;

joinClause {
		FromItem fromItem = null;
		Expression on = null;
	}
	: #(COMMA joinFromExpression) {
	} |
	#(LEFT fromItem=joinExpression on=onClause) {
		getCurrentSelectFragment().addJoin(new Join(JoinType.LEFT, fromItem, on));
	} |
	#(RIGHT fromItem=joinExpression on=onClause) {
		getCurrentSelectFragment().addJoin(new Join(JoinType.RIGHT, fromItem, on));
	} |
	#(JOIN fromItem=joinExpression on=onClause) {
		getCurrentSelectFragment().addJoin(new Join(JoinType.INNER, fromItem, on));
	}
	;

joinExpression returns [FromItem fromItem] {
	fromItem = null;
	Expression where = null;
	Alias alias = null;
	}
	: c1:IDENT (a1:IDENT)? {
		fromItem = createTable(#c1, #a1, false);
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		fromItem = createTable(#c2, #a2, true);
	} | #(OPEN { 
			selectFragmentStack.push(new SubQuery());
			}
			s:selectClause
			f:fromClause
			where=whereClause {getCurrentSelectFragment().setWhere(where);}
			(orderByClause)?
			(limitClause)?
			alias=aliasedSuffix {
				((SubQuery)getCurrentSelectFragment()).setAlias(alias);
			}
		) {
		fromItem = popSelectFragmentStack(alias);
	} 
	;

selectFromExpression {
	Expression where = null;
	Alias alias = null;
}
	: c1:IDENT (a1:IDENT)? {
		getCurrentSelectFragment().setFromItem(createTable(#c1, #a1, false));
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		getCurrentSelectFragment().setFromItem(createTable(#c2, #a2, true));
	} | #(OPEN { 
			createStatement(SELECT); 
			}
			s:selectClause
			f:fromClause
			where=whereClause {getCurrentSelectFragment().setWhere(where);}
			(orderByClause)?
			(limitClause)?
			alias=aliasedSuffix {
				((SubQuery)getCurrentSelectFragment()).setAlias(alias);
			}
		) {
		popSelectFragmentStack(alias);
	} 
	;

updateFromExpression
	: c1:IDENT (a1:IDENT)? {
		statement.setFromItem(createTable(#c1, #a1, false));
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		statement.setFromItem(createTable(#c2, #a2, true));
	}
	;


joinFromExpression {
	Expression where = null;
	Alias alias = null;
}
	: c1:IDENT (a1:IDENT)? {
		getFirstSelectFragment().addFromItem(createTable(#c1, #a1, false));
	} | #(AS 
			c2:IDENT 
			a2:IDENT
		) {
		getFirstSelectFragment().addFromItem(createTable(#c2, #a2, true));
	} | #(OPEN { 
			selectFragmentStack.push(new SubQuery());
			}
			s:selectClause
			f:fromClause
			where=whereClause {getCurrentSelectFragment().setWhere(where);}
			(orderByClause)?
			(limitClause)?
			alias=aliasedSuffix {
				((SubQuery)getCurrentSelectFragment()).setAlias(alias);
			}
		) {
		getFirstSelectFragment().addFromItem(popSelectFragmentStack(alias));
	} 
	;
	
whereClause returns [Expression expr] {
		expr = null;
	}
	: #(WHERE expr=logicalExpression) {
	}
	;

onClause returns [Expression expr] {
		expr = null;
	}
	: #(ON expr=logicalExpression) {
	}
	;

orderByClause
	: #(ORDER
		orderByExpr 
		(COMMA! orderByExpr)*)
	;

orderByExpr
	: i:IDENT {
		getCurrentSelectFragment().addOrderByExpr(new OrderByExpr(tableMap, subQueryMap, #i.getText())); 
	} | 
	#(ASC a:IDENT) {
		getCurrentSelectFragment().addOrderByExpr(new OrderByExpr(tableMap, subQueryMap, #a.getText(), OrderByType.ASC)); 
	} | 
	#(DESC d:IDENT) {
		getCurrentSelectFragment().addOrderByExpr(new OrderByExpr(tableMap, subQueryMap, #d.getText(), OrderByType.DESC));
	}
	;

limitClause 
	: #(LIMIT 
		i:NUMERICAL 
		(j:NUMERICAL)?) {
		if (#j == null) {
			getCurrentSelectFragment().setRowCount(new Numerical(#i.getText()));
		} else {
			getCurrentSelectFragment().setLimit(new Numerical(#i.getText()), new Numerical(#j.getText()));
		}
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
	: #(VALUES variable (variable)*) |
	#(VALUE variable (variable)*)
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
		updateFromExpression) {
	} 
	;

setClause
	: #(SET
		(setEqualityExpression)+ {
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

setEqualityExpression returns [Expression expr] {
		expr = null;
	}
	: expr=setEqualsToExpression 
	;

setEqualsToExpression returns [Expression expr] {
		expr = null;
		Expression rr = null;
	}
	: #(EQ 
			ll:IDENT {
		}
			rr=variable {
		}) {
		expr = new EqualsTo(createColumn(#ll.getText()), rr);
		if (rr instanceof Param) {
			List<Integer> indexes = paramIndexMap.get(#ll.getText());
			if (indexes == null) {
				indexes = new ArrayList<Integer>();
				paramIndexMap.put(#ll.getText(), indexes);
			}
			indexes.add(++paramIndex);
		} else if (rr instanceof RealNumber) {
		} else if (rr instanceof Numerical) {
		} else {
			throw new UnsupportedOperationException();
		}
	}
	;

equalsToExpression returns [Expression expr] {
		expr = null;
		Expression rr = null;
		Column column = null;
	}
	: #(EQ 
			ll:IDENT {
			column = createColumn(#ll.getText());
		}
			rr=constant {
		}) {
		expr = new EqualsTo(createColumn(#ll.getText()), rr);
		if (rr instanceof Param) {
			List<Integer> indexes = paramIndexMap.get(#ll.getText());
			if (indexes == null) {
				indexes = new ArrayList<Integer>();
				paramIndexMap.put(#ll.getText(), indexes);
			}
			indexes.add(++paramIndex);
		} else if (rr instanceof RealNumber) {
			List<String> values = paramValueMap.get(#ll.getText());
			if (values == null) {
				values = new ArrayList<String>();
				paramValueMap.put(#ll.getText(), values);
			}
			values.add(rr.toStr());
		} else if (rr instanceof Numerical) {
			List<String> values = paramValueMap.get(#ll.getText());
			if (values == null) {
				values = new ArrayList<String>();
				paramValueMap.put(#ll.getText(), values);
			}
			values.add(rr.toStr());
		} else if (rr instanceof Column) {
			if (ll != null) {
				columnEqualMap.put(column, (Column) rr);
				columnEqualMap.put((Column) rr, column);
			}
		} else {
			throw new UnsupportedOperationException();
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
	: i:IDENT {value = createColumn(#i.getText());}
	;

variable returns [Expression value] {
		value = null;
	}
	: r:REAL_NUMBER {value = new RealNumber(#r.getText());}
	| n:NUMERICAL {value = new Numerical(#n.getText());}
	| q:QUOTED_STRING {value = new QuotedString(#q.getText());}
	| PARAM {value = new Param();}
	;