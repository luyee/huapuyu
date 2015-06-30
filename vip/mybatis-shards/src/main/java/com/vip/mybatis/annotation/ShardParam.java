package com.vip.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang.StringUtils;

/**
 * shard annotation
 * 
 * @author Anders
 * 
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ShardParam {
	/**
	 * shard strategy name
	 */
	String name() default StringUtils.EMPTY;

	/**
	 * shard object's field
	 */
	String field() default StringUtils.EMPTY;
}
