package com.anders.ethan.log.cat.client.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

// @Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
@Activate(group = { com.alibaba.dubbo.common.Constants.PROVIDER })
public class DubboProviderFilter implements Filter {

	private static Logger LOGGER = LoggerFactory
			.getLogger(DubboProviderFilter.class);

	public Result invoke(Invoker<?> invoker, final Invocation invocation)
			throws RpcException {
		Transaction transaction;
		String methodName = null;
		String consumerIp = null;
		try {
			methodName = invoker.getInterface().getName() + "::"
					+ invocation.getMethodName();
			consumerIp = RpcContext.getContext().getRemoteHost() + ":"
					+ RpcContext.getContext().getRemotePort();

			Cat.logRemoteCallServer(new Cat.Context() {
				@Override
				public void addProperty(String key, String value) {
					invocation.getAttachments().put(key, value);
				}

				@Override
				public String getProperty(String key) {
					return invocation.getAttachments().get(key);
				}
			});

			transaction = Cat.newTransaction(Constants.TRANS_TYPE_PROVIDER,
					methodName);

			debugLog(">>>>>>>>>>>>>>>> " + methodName, consumerIp);

			Cat.logEvent(Constants.TRANS_TYPE_PROVIDER + ".client", consumerIp);
		} catch (Throwable e) {
			// TODO Anders 是否要添加cat error log
			// LOGGER.error("failed to create cat transaction", e);
			Cat.logError(e);
			errorLog("failed to create cat transaction", e);
			return invoker.invoke(invocation);
		}

		Result result = null;
		try {
			result = invoker.invoke(invocation);
			transaction.setStatus(Transaction.SUCCESS);
			return result;
		} catch (Throwable e) {
			Cat.logError(e);
			errorLog(null, e);
			transaction.setStatus(e);
			throw e;
		} finally {
			transaction.complete();
			debugLog("<<<<<<<<<<<<<<<< " + methodName, consumerIp);
		}
	}

	private void debugLog(String log, String info) {
		LOGGER.debug(log
				+ " [type:"
				+ Constants.TRANS_TYPE_PROVIDER
				+ ", info:"
				+ info
				+ ", traceId:"
				+ Cat.getManager().getThreadLocalMessageTree()
						.getParentMessageId() + "]");
	}

	private void errorLog(String log, Throwable e) {
		LOGGER.error(log
				+ " [type:"
				+ Constants.TRANS_TYPE_PROVIDER
				+ ", traceId:"
				+ Cat.getManager().getThreadLocalMessageTree()
						.getParentMessageId() + "]", e);
	}
}