package com.anders.ethan.log.cat.client.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.anders.ethan.log.cat.client.common.Constants;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

public class ServiceInterceptor implements MethodInterceptor {

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

			debugLog(">>>>>>>>>>>>>>>> " + methodName, methodName);

			Assert.notNull(transaction);
		} catch (Throwable e) {
			// TODO Anders 是否要添加cat error log
			// LOGGER.error("failed to create cat transaction", e);
			Cat.logError(e);
			errorLog("failed to create cat transaction", e);
			return invocation.proceed();
		}

		try {
			object = invocation.proceed();
			transaction.setStatus(Transaction.SUCCESS);
			return object;
		} catch (Throwable e) {
			Cat.logError(e);
			errorLog(null, e);
			transaction.setStatus(e);
			throw e;
		} finally {
			transaction.complete();
			debugLog("<<<<<<<<<<<<<<<< " + methodName, methodName);
		}
	}

	private void debugLog(String log, String info) {
		LOGGER.debug(log
				+ " [type:"
				+ Constants.TRANS_TYPE_SERVICE
				+ ", info:"
				+ info
				+ ", traceId:"
				+ Cat.getManager().getThreadLocalMessageTree()
						.getParentMessageId() + "]");
	}

	private void errorLog(String log, Throwable e) {
		LOGGER.error(log
				+ " [type:"
				+ Constants.TRANS_TYPE_SERVICE
				+ ", traceId:"
				+ Cat.getManager().getThreadLocalMessageTree()
						.getParentMessageId() + "]", e);
	}
}
