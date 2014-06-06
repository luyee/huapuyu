package com.anders.ssh.mybatis;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getSelectListPhrase;
import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MySQLPagePlugin extends PluginAdapter {

	public MySQLPagePlugin() {
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addLimitFieldAndGetSet(topLevelClass, introspectedTable, "limitStart");
		addLimitFieldAndGetSet(topLevelClass, introspectedTable, "limitCount");
		return true;
	}

	@Override
	public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		method.getBodyLines().clear();
		method.addBodyLine("BEGIN();");

		boolean distinctCheck = true;
		for (IntrospectedColumn introspectedColumn : introspectedTable.getNonBLOBColumns()) {
			if (distinctCheck) {
				method.addBodyLine("if (example != null && example.isDistinct()) {");
				method.addBodyLine(String.format("SELECT_DISTINCT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
				method.addBodyLine("} else {");
				method.addBodyLine(String.format("SELECT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
				method.addBodyLine("}");
			}
			else {
				method.addBodyLine(String.format("SELECT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
			}

			distinctCheck = false;
		}

		method.addBodyLine(String.format("FROM(\"%s\");", escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
		method.addBodyLine("applyWhere(example, false);");

		method.addBodyLine("");
		method.addBodyLine("if (example != null && example.getOrderByClause() != null) {");
		method.addBodyLine("ORDER_BY(example.getOrderByClause());");
		method.addBodyLine("if (example.getLimitStart() >= 0) {");
		method.addBodyLine("SET(\"limit #{example.limitStart}\");");
		method.addBodyLine("if (example.getLimitCount() >= 0) {");
		method.addBodyLine("SET(\", #{example.limitCount}\");");
		method.addBodyLine("}");
		method.addBodyLine("}");
		method.addBodyLine("}");

		method.addBodyLine("");
		method.addBodyLine("return SQL();");

		return true;
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isOrderByElemen = (XmlElement) element.getElements().get(element.getElements().size() - 1);

		// 创建limit start标签
		XmlElement isGreaterEqualStartElement = new XmlElement("isGreaterEqual");
		isGreaterEqualStartElement.addAttribute(new Attribute("property", "limitStart"));
		isGreaterEqualStartElement.addAttribute(new Attribute("compareValue", "0"));
		isGreaterEqualStartElement.addElement(new TextElement("limit $limitStart$"));

		// 创建limit count标签
		XmlElement isGreaterEqualCountElement = new XmlElement("isGreaterEqual");
		isGreaterEqualCountElement.addAttribute(new Attribute("property", "limitCount"));
		isGreaterEqualCountElement.addAttribute(new Attribute("compareValue", "0"));
		isGreaterEqualCountElement.addElement(new TextElement(", $limitCount$"));

		isGreaterEqualStartElement.addElement(isGreaterEqualCountElement);

		isOrderByElemen.addElement(isGreaterEqualStartElement);
		return true;
	}

	private void addLimitFieldAndGetSet(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);

		CommentGenerator commentGenerator = context.getCommentGenerator();

		// 生成字段
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(FullyQualifiedJavaType.getIntInstance());
		field.setName(name);
		field.setInitializationString("-1");
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);

		// 生成set方法
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
		method.addBodyLine("this." + name + " = " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);

		// 生成get方法
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}
}
