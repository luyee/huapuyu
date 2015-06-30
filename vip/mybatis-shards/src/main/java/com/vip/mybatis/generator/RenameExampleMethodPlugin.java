package com.vip.mybatis.generator;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class RenameExampleMethodPlugin extends PluginAdapter {

	private String searchString;
	private String replaceString;
	private Pattern pattern;

	public RenameExampleMethodPlugin() {
	}

	@Override
	public boolean validate(List<String> warnings) {

		searchString = properties.getProperty("searchString");
		replaceString = properties.getProperty("replaceString");

		boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

		if (valid) {
			pattern = Pattern.compile(searchString);
		}
		else {
			if (!stringHasValue(searchString)) {
				warnings.add(getString("ValidationError.18", "RenameExampleClassPlugin", "searchString"));
			}
			if (!stringHasValue(replaceString)) {
				warnings.add(getString("ValidationError.18", "RenameExampleClassPlugin", "replaceString"));
			}
		}

		return valid;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		introspectedTable.setCountByExampleStatementId(rename(introspectedTable.getCountByExampleStatementId()));// introspectedTable.setCountByExampleStatementId("countByExample");
		introspectedTable.setDeleteByExampleStatementId(rename(introspectedTable.getDeleteByExampleStatementId()));// introspectedTable.setDeleteByExampleStatementId("deleteByExample");
		// introspectedTable.setDeleteByPrimaryKeyStatementId("deleteByPrimaryKey");
		// introspectedTable.setInsertStatementId("insert");
		// introspectedTable.setInsertSelectiveStatementId("insertSelective");
		// introspectedTable.setSelectAllStatementId("selectAll");
		introspectedTable.setSelectByExampleStatementId(rename(introspectedTable.getSelectByExampleStatementId()));// introspectedTable.setSelectByExampleStatementId("selectByExample");
		introspectedTable.setSelectByExampleWithBLOBsStatementId(rename(introspectedTable.getSelectByExampleWithBLOBsStatementId()));// introspectedTable.setSelectByExampleWithBLOBsStatementId("selectByExampleWithBLOBs");
		// introspectedTable.setSelectByPrimaryKeyStatementId("selectByPrimaryKey");
		introspectedTable.setUpdateByExampleStatementId(rename(introspectedTable.getUpdateByExampleStatementId()));// introspectedTable.setUpdateByExampleStatementId("updateByExample");
		introspectedTable.setUpdateByExampleSelectiveStatementId(rename(introspectedTable.getUpdateByExampleSelectiveStatementId()));// introspectedTable.setUpdateByExampleSelectiveStatementId("updateByExampleSelective");
		introspectedTable.setUpdateByExampleWithBLOBsStatementId(rename(introspectedTable.getUpdateByExampleWithBLOBsStatementId()));// introspectedTable.setUpdateByExampleWithBLOBsStatementId("updateByExampleWithBLOBs");
		// introspectedTable.setUpdateByPrimaryKeyStatementId("updateByPrimaryKey");
		// introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("updateByPrimaryKeySelective");
		// introspectedTable.setUpdateByPrimaryKeyWithBLOBsStatementId("updateByPrimaryKeyWithBLOBs");
		// introspectedTable.setBaseResultMapId("BaseResultMap");
		// introspectedTable.setResultMapWithBLOBsId("ResultMapWithBLOBs");
		introspectedTable.setExampleWhereClauseId(rename(introspectedTable.getExampleWhereClauseId()));// introspectedTable.setExampleWhereClauseId("Example_Where_Clause");
		// introspectedTable.setBaseColumnListId("Base_Column_List");
		// introspectedTable.setBlobColumnListId("Blob_Column_List");
		introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(rename(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId()));// introspectedTable.setMyBatis3UpdateByExampleWhereClauseId("Update_By_Example_Where_Clause");

	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		renameMethod(interfaze.getMethods());

		// 参数名为example的改名为criteria
		// List<Parameter> params = method.getParameters();
		// for (Parameter parameter : params) {
		// try {
		// Field field = Parameter.class.getDeclaredField("name");
		// makeAccessible(field);
		// setField(field, parameter, rename(parameter.getName()));
		// }
		// catch (SecurityException e) {
		// e.printStackTrace();
		// }
		// catch (NoSuchFieldException e) {
		// e.printStackTrace();
		// }
		// }

		// 给Mapper接口添加泛型父接口
		// Set<FullyQualifiedJavaType> fullyQualifiedJavaTypeSet = interfaze.getSuperInterfaceTypes();
		// if (fullyQualifiedJavaTypeSet != null && fullyQualifiedJavaTypeSet.size() > 0) {
		// FullyQualifiedJavaType[] fullyQualifiedJavaTypes = new FullyQualifiedJavaType[fullyQualifiedJavaTypeSet.size()];
		// fullyQualifiedJavaTypeSet.toArray(fullyQualifiedJavaTypes);
		// fullyQualifiedJavaTypes[0].addTypeArgument(new FullyQualifiedJavaType(Long.class.getName()));
		// fullyQualifiedJavaTypes[0].addTypeArgument(new FullyQualifiedJavaType(interfaze.getType().getShortName().replaceAll("Mapper", "")));
		// fullyQualifiedJavaTypes[0].addTypeArgument(new FullyQualifiedJavaType(interfaze.getType().getShortName().replaceAll("Mapper", "Criteria")));
		// }

		return true;
	}

	@Override
	public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		renameMethod(topLevelClass.getMethods());
		return true;
	}

	private void renameMethod(List<Method> methods) {
		if (methods != null && methods.size() > 0) {
			for (Method method : methods) {
				method.setName(rename(method.getName()));
			}
		}
	}

	private String rename(String name) {
		Matcher matcher = pattern.matcher(name);
		return matcher.replaceAll(replaceString);
	}

	// private void makeAccessible(Field field) {
	// if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
	// field.setAccessible(true);
	// }
	// }
	//
	// public void setField(Field field, Object target, Object value) {
	// try {
	// field.set(target, value);
	// }
	// catch (IllegalAccessException ex) {
	// throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
	// }
	// }
}
