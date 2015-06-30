package com.anders.ssh.datasource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.anders.ssh.datasource.annotation.DatasourceKeyAnnotation;

/**
 * 类MyDynamicDatasourceKeyInteceptor.java的实现描述：
 * 
 * @author huangyiping 2012-4-16 下午04:53:26
 */
public class MyDynamicDatasourceInteceptor implements MethodInterceptor {

	/**
	 * 数据源key的存储控制器
	 */
	private DynamicDataSourceKey dataSourceKey;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		DatasourceKeyAnnotation datasourceKeyAnnotation = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes()).getAnnotation(DatasourceKeyAnnotation.class);
		if (datasourceKeyAnnotation == null) {
			return invocation.proceed();
		}

		String currentKey = dataSourceKey.getKey(); // 不会为null

		try {
			String annotationKey = datasourceKeyAnnotation.datasourceKeyType();
			dataSourceKey.clearKey();

			if ("READ".equalsIgnoreCase(annotationKey)) {
				dataSourceKey.setReadKey();
			}
			else if ("WRITE".equalsIgnoreCase(annotationKey)) {
				dataSourceKey.setWriteKey();
			}
			else {
				dataSourceKey.setKey(annotationKey);
			}

			return invocation.proceed();
		}
		finally {

			dataSourceKey.setKey(currentKey);
		}
	}

	/**
	 * @param dataSourceKey
	 *            the dataSourceKey to set
	 */
	public void setDataSourceKey(DynamicDataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

}
