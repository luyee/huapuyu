package com.anders.ethan.log.cat.client.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.anders.ethan.log.cat.client.common.Constants;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

//@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
//@Activate(group = { com.alibaba.dubbo.common.Constants.CONSUMER })
public class DubboConsumerFilter implements Filter, Constants {

	private static Logger LOGGER = LoggerFactory
			.getLogger(DubboConsumerFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, final Invocation invocation)
			throws RpcException {

		Transaction transaction = null;
		String methodName = null;
		String providerIp = null;
		try {
			methodName = invoker.getInterface().getName() + "::"
					+ invocation.getMethodName();
			providerIp = RpcContext.getContext().getRemoteHost() + ":"
					+ RpcContext.getContext().getRemotePort();

			transaction = Cat.newTransaction(Constants.TRANS_TYPE_CONSUMER,
					methodName);

			LOGGER.debug(LOG_DUBBO_CONSUMER_IN_MSG, methodName, providerIp);

			Cat.logEvent(Constants.TRANS_TYPE_CONSUMER + ".server", providerIp);
			Cat.logRemoteCallClient(new Cat.Context() {
				@Override
				public void addProperty(String key, String value) {
					invocation.getAttachments().put(key, value);
				}

				@Override
				public String getProperty(String key) {
					return invocation.getAttachments().get(key);
				}
			});

			Assert.notNull(transaction);
		} catch (Exception e) {
			Cat.logError(e);
			LOGGER.error(LOG_FAILED_TO_CREATE_TRANS, e);
			return invoker.invoke(invocation);
		}

		Result result = null;
		try {
			result = invoker.invoke(invocation);
			transaction.setStatus(Transaction.SUCCESS);
			return result;
		} catch (Throwable e) {
			Cat.logError(e);
			LOGGER.error(LOG_DUBBO_CONSUMER_EX_MSG, methodName, providerIp, e);
			transaction.setStatus(e);
			throw e;
		} finally {
			// TODO Anders complete和打日志的顺序是否要调整，其他语句类似
			transaction.complete();
			LOGGER.debug(LOG_DUBBO_CONSUMER_OUT_MSG, methodName, providerIp);
		}
	}
}