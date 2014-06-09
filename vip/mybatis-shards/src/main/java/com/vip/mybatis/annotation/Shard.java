package com.vip.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang.StringUtils;

/**
 * 分片注解
 * 
 * @author Anders
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Shard {
	String name() default StringUtils.EMPTY;

	Class<?> classType();

	Class<?> fieldType();

	String fieldName() default StringUtils.EMPTY;
}
