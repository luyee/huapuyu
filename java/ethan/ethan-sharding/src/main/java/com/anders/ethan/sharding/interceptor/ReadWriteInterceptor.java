package com.anders.ethan.sharding.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ethan.sharding.common.ReadWriteKey;
import com.anders.ethan.sharding.common.ShardingUtil;

public class ReadWriteInterceptor implements MethodInterceptor {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadWriteInterceptor.class);

	private ReadWriteKey readWriteKey;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String debugInfo = "[" + invocation.toString() + "]";
		LOGGER.debug("enter into read/write interceptor {}", debugInfo);

		Method method = invocation
				.getThis()
				.getClass()
				.getMethod(invocation.getMethod().getName(),
						invocation.getMethod().getParameterTypes());

		Transactional tx = method.getAnnotation(Transactional.class);
		if (tx == null) {
			tx = invocation.getThis().getClass()
					.getAnnotation(Transactional.class);
		}

		if (tx != null && tx.readOnly()) {
			readWriteKey.setReadKey();
		} else {
			readWriteKey.setWriteKey();
		}

		try {
			return invocation.proceed();
		} catch (Throwable e) {
			throw e;
		} finally {
			ShardingUtil.removeCurrent();
			LOGGER.debug("get out read/write interceptor {}", debugInfo);
		}
	}

	public ReadWriteKey getReadWriteKey() {
		return readWriteKey;
	}

	public void setReadWriteKey(ReadWriteKey readWriteKey) {
		this.readWriteKey = readWriteKey;
	}
}
