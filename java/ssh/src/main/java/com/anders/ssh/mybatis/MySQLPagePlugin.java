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
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.JavaBeansUtil;

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

		// InnerClass criteria = null;
		//
		// for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
		// if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) {
		// criteria = innerClass;
		// break;
		// }
		// }
		//
		// if (criteria == null) {
		// return true;
		// }
		//
		// for (IntrospectedColumn introspectedColumn : introspectedTable.getNonBLOBColumns()) {
		// addSelectFieldAndGetSet(criteria, introspectedTable, introspectedColumn.getJavaProperty());
		// }

		// get clear method
		List<Method> methods = topLevelClass.getMethods();
		Method method = null;
		for (Method m : methods) {
			if (m.getName().equals("clear")) {
				method = m;
				break;
			}
		}

		if (method == null)
			return false;

		method.addBodyLine("limitStart = -1;");
		method.addBodyLine("limitCount = -1;");

		// generate isCustomizeSelect method
		Method isOptionFieldMethod = new Method();
		isOptionFieldMethod.setVisibility(JavaVisibility.PUBLIC);
		isOptionFieldMethod.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
		isOptionFieldMethod.setName("isCustomizeSelect");

		StringBuilder sb = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : introspectedTable.getNonBLOBColumns()) {
			addSelectFieldAndGetSet(topLevelClass, introspectedTable, introspectedColumn.getJavaProperty());
			method.addBodyLine(introspectedColumn.getJavaProperty() + " = false;");
			sb.append(introspectedColumn.getJavaProperty() + " | ");
		}

		String isCustomizeSelectBody = sb.toString();
		if (isCustomizeSelectBody.length() == 0) {
			return false;
		}

		isOptionFieldMethod.addBodyLine("return " + isCustomizeSelectBody.substring(0, isCustomizeSelectBody.length() - 2) + ";");
		topLevelClass.addMethod(isOptionFieldMethod);

		return true;
	}

	// @Override
	// public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
	// Set importedTypes = new TreeSet();
	//
	// method.getParameters().clear();
	//
	// FullyQualifiedJavaType example = new FullyQualifiedJavaType(introspectedTable.getExampleType());
	// method.addParameter(new Parameter(example, "example", "@Param(\"example\")"));
	// importedTypes.add(example);
	//
	// FullyQualifiedJavaType fieldsType = new FullyQualifiedJavaType("java.lang.String[]");
	// method.addParameter(new Parameter(fieldsType, "fields", "@Param(\"fields\")"));
	// importedTypes.add(fieldsType);
	//
	// importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
	//
	// interfaze.addImportedTypes(importedTypes);
	//
	// return true;
	// }

	@Override
	public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// method.getParameters().clear();
		method.getBodyLines().clear();

		// Set importedTypes = new TreeSet();
		// importedTypes.add(new FullyQualifiedJavaType("java.util.Map"));
		//
		// method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Map<java.lang.String, java.lang.Object>"), "parameter"));
		//
		// FullyQualifiedJavaType example = new FullyQualifiedJavaType(introspectedTable.getExampleType());
		// importedTypes.add(example);
		// method.addBodyLine(String.format("%s example = (%s) parameter.get(\"example\");", new Object[] { example.getShortName(), example.getShortName() }));
		//
		// FullyQualifiedJavaType fields = new FullyQualifiedJavaType("java.lang.String[]");
		// importedTypes.add(fields);
		// method.addBodyLine(String.format("%s fields = (%s) parameter.get(\"fields\");", new Object[] { fields.getShortName(), fields.getShortName() }));

		method.addBodyLine("BEGIN();");
		method.addBodyLine("if (example != null && example.isCustomizeSelect()) {");

		StringBuilder sbIf = new StringBuilder();
		StringBuilder sbElse = new StringBuilder();
		boolean distinctCheck = true;

		List<IntrospectedColumn> introspectedColumnList = introspectedTable.getNonBLOBColumns();
		if (introspectedColumnList == null || introspectedColumnList.size() <= 0)
			return false;

		for (IntrospectedColumn introspectedColumn : introspectedColumnList) {
			if (distinctCheck) {
				sbIf.append("if (example.is" + JavaBeansUtil.getCamelCaseString(introspectedColumn.getJavaProperty(), true) + "()) {");
				sbIf.append("if (example.isDistinct()) {");
				sbIf.append(String.format("SELECT_DISTINCT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
				sbIf.append("} else {");
				sbIf.append(String.format("SELECT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
				sbIf.append("}");
				sbIf.append("}");

				sbElse.append("if (example != null && example.isDistinct()) {");
				sbElse.append(String.format("SELECT_DISTINCT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
				sbElse.append("} else {");
				sbElse.append(String.format("SELECT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
				sbElse.append("}");
			}
			else {
				sbIf.append("if (example.is" + JavaBeansUtil.getCamelCaseString(introspectedColumn.getJavaProperty(), true) + "()) {");
				sbIf.append(String.format("SELECT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
				sbIf.append("}");

				sbElse.append(String.format("SELECT(\"%s\");", escapeStringForJava(getSelectListPhrase(introspectedColumn))));
			}

			distinctCheck = false;
		}

		method.addBodyLine(sbIf.toString());
		method.addBodyLine("}");
		method.addBodyLine("else {");
		method.addBodyLine(sbElse.toString());
		method.addBodyLine("}");
		method.addBodyLine(String.format("FROM(\"%s\");", escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
		method.addBodyLine("applyWhere(example, false);");

		method.addBodyLine("");
		method.addBodyLine("StringBuilder sb = new StringBuilder();");
		method.addBodyLine("if (example != null && example.getOrderByClause() != null) {");
		method.addBodyLine("ORDER_BY(example.getOrderByClause());");
		method.addBodyLine("sb.append(SQL());");
		method.addBodyLine("if (example.getLimitStart() >= 0) {");
		method.addBodyLine("sb.append(\" limit #{limitStart}\");");
		method.addBodyLine("if (example.getLimitCount() >= 0) {");
		method.addBodyLine("sb.append(\", #{limitCount}\");");
		method.addBodyLine("}");
		method.addBodyLine("}");
		method.addBodyLine("}");
		method.addBodyLine("else {");
		method.addBodyLine("sb.append(SQL());");
		method.addBodyLine("}");

		method.addBodyLine("");
		method.addBodyLine("return sb.toString();");

		// topLevelClass.addImportedTypes(importedTypes);

		return true;
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isOrderByElemen = (XmlElement) element.getElements().get(element.getElements().size() - 1);

		// generate limit start xml
		XmlElement isGreaterEqualStartElement = new XmlElement("isGreaterEqual");
		isGreaterEqualStartElement.addAttribute(new Attribute("property", "limitStart"));
		isGreaterEqualStartElement.addAttribute(new Attribute("compareValue", "0"));
		isGreaterEqualStartElement.addElement(new TextElement("limit $limitStart$"));

		// generate limit count xml
		XmlElement isGreaterEqualCountElement = new XmlElement("isGreaterEqual");
		isGreaterEqualCountElement.addAttribute(new Attribute("property", "limitCount"));
		isGreaterEqualCountElement.addAttribute(new Attribute("compareValue", "0"));
		isGreaterEqualCountElement.addElement(new TextElement(", $limitCount$"));

		isGreaterEqualStartElement.addElement(isGreaterEqualCountElement);

		isOrderByElemen.addElement(isGreaterEqualStartElement);
		return true;
	}

	private void addLimitFieldAndGetSet(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		CommentGenerator commentGenerator = context.getCommentGenerator();

		// generate field
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(FullyQualifiedJavaType.getIntInstance());
		field.setName(name);
		field.setInitializationString("-1");
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);

		// generate get method
		topLevelClass.addMethod(AbstractJavaGenerator.getGetter(field));

		// generate set method
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(JavaBeansUtil.getSetterMethodName(field.getName()));
		method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
		method.addBodyLine("this." + name + " = " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	private void addSelectFieldAndGetSet(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		CommentGenerator commentGenerator = context.getCommentGenerator();

		// generate field
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
		field.setName(name);
		field.setInitializationString("false");
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);

		// generate is method
		topLevelClass.addMethod(AbstractJavaGenerator.getGetter(field));

		// generate set method
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(topLevelClass.getType());
		method.setName("add" + JavaBeansUtil.getCamelCaseString(name, true));
		// method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "bool"));
		method.addBodyLine("this." + name + " = true;");
		method.addBodyLine("return this;");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}
}
