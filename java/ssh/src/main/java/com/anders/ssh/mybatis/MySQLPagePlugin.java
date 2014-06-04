package com.anders.ssh.mybatis;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getEscapedColumnName;
import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getParameterClause;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getGetterMethodName;
import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class MySQLPagePlugin extends PluginAdapter {

	public MySQLPagePlugin() {
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

		return true;
	}

	@Override
	public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

		Set<String> staticImports = new TreeSet<String>();
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN"); //$NON-NLS-1$
		staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO"); //$NON-NLS-1$
		staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL"); //$NON-NLS-1$
		staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.VALUES"); //$NON-NLS-1$

		FullyQualifiedJavaType fqjt = introspectedTable.getRules().calculateAllFieldsClass();
		importedTypes.add(fqjt);

		Method method = new Method("test");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		method.addParameter(new Parameter(fqjt, "record"));

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		method.addBodyLine("BEGIN();");
		method.addBodyLine(String.format("INSERT_INTO(\"%s\");", escapeStringForJava(introspectedTable.getFullyQualifiedTableNameAtRuntime())));

		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
			if (introspectedColumn.isIdentity()) {
				// cannot set values on identity fields
				continue;
			}

			method.addBodyLine("");
			if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive() && !introspectedColumn.isSequenceColumn()) {
				method.addBodyLine(String.format("if (record.%s() != null) {", getGetterMethodName(introspectedColumn.getJavaProperty(), introspectedColumn.getFullyQualifiedJavaType())));
			}
			method.addBodyLine(String.format("VALUES(\"%s\", \"%s\");", escapeStringForJava(getEscapedColumnName(introspectedColumn)), getParameterClause(introspectedColumn)));
			if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive() && !introspectedColumn.isSequenceColumn()) {
				method.addBodyLine("}"); //$NON-NLS-1$
			}
		}

		method.addBodyLine("");
		method.addBodyLine("return SQL();");

		if (context.getPlugins().providerInsertSelectiveMethodGenerated(method, topLevelClass, introspectedTable)) {
			topLevelClass.addStaticImports(staticImports);
			topLevelClass.addImportedTypes(importedTypes);
			topLevelClass.addMethod(method);
		}

		return true;
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		return true;
	}
}
