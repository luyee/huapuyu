package com.anders.vote.mybatis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.anders.vote.mybatis.annotation.Id;
import com.anders.vote.mybatis.annotation.OptimisticLocking;
import com.anders.vote.mybatis.annotation.Version;

public class EntityWrapper {

	private final static Logger logger = Logger.getLogger(EntityWrapper.class);

	private Object entityObject;

	private Class<?> entityClass;

	private String tableName = null;

	private String identityColumnName = null;

	private String versionColumnName = null;

	private Field identityField = null;

	private Field versionField = null;

	static boolean hasOptimisticLockingAnnotation(Object entityObject) {
		if (entityObject == null) {
			return false;
		}
		return entityObject.getClass().isAnnotationPresent(OptimisticLocking.class);
	}

	EntityWrapper(Object entityObject) {
		this.entityObject = entityObject;
		entityClass = this.entityObject.getClass();
		validateAndExtractAnnotations();
	}

	public String getTableName() {
		return tableName;
	}

	String getIdentityColumnName() {
		return identityColumnName;
	}

	public String getVersionColumnName() {
		return versionColumnName;
	}

	void setIdentity(Object identity) {
		boolean accessChanged = false;
		if (!identityField.isAccessible()) {
			identityField.setAccessible(true);
			accessChanged = true;
		}
		try {
			if (identity instanceof Long || identity instanceof Integer) {
				identityField.set(entityObject, identity);
			}
			else {
				throw new RuntimeException("Type '" + identity.getClass().getName() + "' is neither java.lang.Long or java.lang.Integer, unable to set identity.");
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			if (accessChanged) {
				identityField.setAccessible(false);
			}
		}
	}

	void incrementVersion() {
		boolean accessChanged = false;
		if (!versionField.isAccessible()) {
			versionField.setAccessible(true);
			accessChanged = true;
		}
		try {
			if (isVersionLong()) {
				Long value = (Long) versionField.get(entityObject);
				if (value == null) {
					value = 0L;
				}
				else {
					value++;
				}
				versionField.set(entityObject, value);
			}
			else if (isVersionInteger()) {
				Integer value = (Integer) versionField.get(entityObject);
				if (value == null) {
					value = 0;
				}
				else {
					value++;
				}
				versionField.set(entityObject, value);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			if (accessChanged) {
				versionField.setAccessible(false);
			}
		}
	}

	void initVersion() {
		boolean accessChanged = false;
		if (!versionField.isAccessible()) {
			versionField.setAccessible(true);
			accessChanged = true;
		}
		try {
			if (isVersionInteger()) {
				versionField.set(entityObject, new Integer(0));
			}
			else if (isVersionLong()) {
				versionField.set(entityObject, new Long(0));
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			if (accessChanged) {
				versionField.setAccessible(false);
			}
		}
	}

	Object getVersion() {
		boolean accessChanged = false;
		if (!versionField.isAccessible()) {
			versionField.setAccessible(true);
			accessChanged = true;
		}
		try {
			return versionField.get(entityObject);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			if (accessChanged) {
				versionField.setAccessible(false);
			}
		}
		return null;
	}

	public Object getIdentity() {
		boolean accessChanged = false;
		if (!identityField.isAccessible()) {
			identityField.setAccessible(true);
			accessChanged = true;
		}
		try {
			return identityField.get(entityObject);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			if (accessChanged) {
				identityField.setAccessible(false);
			}
		}
		return null;
	}

	boolean isStale(Object currentVersion) {
		Long thisVersion = null;
		Long currentValueLong = null;

		if (isVersionInteger()) {
			thisVersion = new Long((Integer) getVersion());
		}
		else if (isVersionLong()) {
			thisVersion = (Long) getVersion();
		}
		else if (versionField != null) {
			logger.warn("Version type is neither Long or Integer: " + versionField.getType());
		}

		if (currentVersion instanceof Long) {
			currentValueLong = (Long) currentVersion;
		}
		else if (currentVersion instanceof Integer) {
			currentValueLong = new Long((Integer) currentVersion);
		}
		else {
			throw new RuntimeException("Version value type '" + currentVersion.getClass().getName() + "' is neither java.lang.Long or java.lang.Integer, unable to verify of object is stale.");
		}
		if (currentValueLong == null || thisVersion == null) {
			logger.warn("Can not determine if object is stale, currentVersion=" + currentValueLong + ";thisVersion=" + thisVersion + ". Returning not stale.");
			return false;
		}
		return currentValueLong > thisVersion;
	}

	private boolean isVersionInteger() {
		return Integer.class.equals(versionField.getType());
	}

	private boolean isVersionLong() {
		return Long.class.equals(versionField.getType());
	}

	private Field recursiveFieldFinder(Class<?> annotatedClass, Class<? extends Annotation> annotationClass) {
		for (Field f : annotatedClass.getDeclaredFields()) {
			if (f.isAnnotationPresent(annotationClass)) {
				return f;
			}
		}
		if (annotatedClass.getSuperclass() != null) {
			return recursiveFieldFinder(annotatedClass.getSuperclass(), annotationClass);
		}
		return null;
	}

	private void validateAndExtractAnnotations() {
		if (!entityClass.isAnnotationPresent(OptimisticLocking.class)) {
			throw new RuntimeException("Class '" + entityClass.getName() + "' is missing the @" + OptimisticLocking.class.getName() + " annotation.");
		}
		tableName = entityClass.getAnnotation(OptimisticLocking.class).value();
		identityField = recursiveFieldFinder(entityClass, Id.class);
		if (identityField == null) {
			throw new RuntimeException("Class '" + entityClass.getName() + "' is missing the @" + Id.class.getName() + " annotation.");
		}
		else {
			identityColumnName = identityField.getAnnotation(Id.class).value();
		}

		if (!identityField.getType().equals(Integer.class) && !identityField.getType().equals(Long.class)) {
			throw new RuntimeException("Field '" + entityClass.getName() + "." + identityField.getName() + "' is the type '" + identityField.getType().getName() + "'. That type is not a valid type for the @" + Id.class.getName() + " annotation. Please use one of java.lang.Integer or java.lang.Long.");
		}

		versionField = recursiveFieldFinder(entityClass, Version.class);

		if (versionField == null) {
			throw new RuntimeException("Class '" + entityClass.getName() + "' is missing the @" + Version.class.getName() + " annotation.");
		}
		else {
			versionColumnName = versionField.getAnnotation(Version.class).value();
		}

		if (!identityField.getType().equals(Integer.class) && !identityField.getType().equals(Long.class)) {
			throw new RuntimeException("Field '" + entityClass.getName() + "." + identityField.getName() + "' is the type '" + identityField.getType().getName() + "'. That type is not a valid type for the @" + Version.class.getName() + " annotation. Please use one of java.lang.Integer or java.lang.Long.");
		}
	}
}
