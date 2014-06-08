package com.vip.datasource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.vip.datasource.annotation.DataSourceAnnotation;
import com.vip.datasource.util.Utils;

/**
 * annotation dynamic datasource interceptor
 * 
 * @author Anders
 * 
 */
public class AnnotationDynamicDatasourceInteceptor implements MethodInterceptor {

	private DynamicDataSourceKey dataSourceKey;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		DataSourceAnnotation dataSourceAnnotation = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes()).getAnnotation(DataSourceAnnotation.class);
		if (dataSourceAnnotation == null) {
			return invocation.proceed();
		}

		try {
			String annotationKey = dataSourceAnnotation.datasourceKeyType();
			dataSourceKey.clearKey();

			if (Utils.isRead(annotationKey)) {
				dataSourceKey.setReadKey();
			}
			else if (Utils.isWrite(annotationKey)) {
				dataSourceKey.setWriteKey();
			}
			else {
				dataSourceKey.setKey(annotationKey);
			}

			return invocation.proceed();
		}
		finally {
			String key = dataSourceKey.getKey();
			dataSourceKey.setKey(key);
		}
	}

	public void setDataSourceKey(DynamicDataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
}
