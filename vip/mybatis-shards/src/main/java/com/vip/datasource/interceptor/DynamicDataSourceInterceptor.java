package com.vip.datasource.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.vip.datasource.DynamicDataSourceKey;
import com.vip.datasource.util.Utils;

/**
 * dynamic datasource interceptor
 * 
 * @author Anders
 * 
 */
public class DynamicDataSourceInterceptor implements MethodInterceptor {
	/**
	 * method to write or read key
	 */
	private Map<String, String> method2KeyMap = new HashMap<String, String>();
	private DynamicDataSourceKey dataSourceKey;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// method name
		final String methodName = invocation.getMethod().getName();
		// match method name
		String matchMethodName = null;
		for (Iterator<String> it = this.method2KeyMap.keySet().iterator(); it.hasNext();) {
			// configure method name in spring
			String cfgMethodName = it.next();
			if (Utils.isMatch(methodName, cfgMethodName) && (matchMethodName == null || matchMethodName.length() <= cfgMethodName.length())) {
				matchMethodName = cfgMethodName;
			}
		}

		// get write or read key
		String key = method2KeyMap.get(matchMethodName);

		if (Utils.isRead(key)) {
			dataSourceKey.setReadKey();
		}
		else if (Utils.isWrite(key)) {
			dataSourceKey.setWriteKey();
		}
		else {
			dataSourceKey.setKey(key);
		}

		return invocation.proceed();
	}

	public void setAttributes(Map<String, String> attributes) {
		this.method2KeyMap = attributes;
	}

	public DynamicDataSourceKey getDataSourceKey() {
		return dataSourceKey;
	}

	public void setDataSourceKey(DynamicDataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

}
