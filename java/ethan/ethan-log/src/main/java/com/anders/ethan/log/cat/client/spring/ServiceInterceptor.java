package com.anders.ethan.log.cat.client.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.anders.ethan.log.cat.client.common.Constants;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

public class ServiceInterceptor implements MethodInterceptor, Constants {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ServiceInterceptor.class);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Transaction transaction = null;
		Object object = null;
		String methodName = null;
		try {
			methodName = invocation.getThis().getClass().getName() + "::"
					+ invocation.getMethod().getName();
			transaction = Cat.newTransaction(Constants.TRANS_TYPE_SERVICE,
					methodName);

			LOGGER.debug(LOG_SERVICE_IN_MSG, methodName);

			Assert.notNull(transaction);
		} catch (Throwable e) {
			// TODO Anders 是否要添加cat error log
			// LOGGER.error("failed to create cat transaction", e);
			Cat.logError(e);
			LOGGER.error(LOG_FAILED_TO_CREATE_TRANS, e);
			return invocation.proceed();
		}

		try {
			object = invocation.proceed();
			transaction.setStatus(Transaction.SUCCESS);
			return object;
		} catch (Throwable e) {
			Cat.logError(e);
			LOGGER.error(LOG_SERVICE_EX_MSG, methodName, e);
			transaction.setStatus(e);
			throw e;
		} finally {
			transaction.complete();
			LOGGER.debug(LOG_SERVICE_OUT_MSG, methodName);
		}
	}
}
