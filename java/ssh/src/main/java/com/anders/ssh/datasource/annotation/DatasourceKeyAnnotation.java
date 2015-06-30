/*
 * Copyright 2000-2011 baidu.com All right reserved. 
 */
package com.anders.ssh.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.anders.ssh.datasource.DynamicDataSourceKeyImpl;

/**
 * 类DatasourceKeyAnnotation.java的实现描述：如果数据源标示位已经设置，是否进行替换操作
 * 
 * @author huangyiping 2012-4-16 下午04:50:00
 */
@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DatasourceKeyAnnotation {

	/**
	 * 读写分离组件，设置当前方法的读写key类型。 "WRITE"与"READ"两种
	 * <p>
	 * 参见{@link DynamicDataSourceKeyImpl}
	 * 
	 * @return
	 */
	String datasourceKeyType() default "WRITE";

}
