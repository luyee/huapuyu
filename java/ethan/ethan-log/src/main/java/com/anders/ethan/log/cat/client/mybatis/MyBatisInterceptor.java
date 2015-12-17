package com.anders.ethan.log.cat.client.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.anders.ethan.log.cat.client.common.Constants;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MyBatisInterceptor implements Interceptor {

	private static Logger LOGGER = LoggerFactory
			.getLogger(MyBatisInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Transaction transaction = null;
		String sql = null;
		String url = null;
		try {
			StatementHandler statementHandler = (StatementHandler) invocation
					.getTarget();
			 sql = statementHandler.getBoundSql().getSql();
			Connection connection = (Connection) invocation.getArgs()[0];
			 url = connection.getMetaData().getURL();

			transaction = Cat.newTransaction(Constants.TRANS_TYPE_JDBC, sql);
			
			debugLog(">>>>>>>>>>>>>>>> " + sql, url);
			
			Cat.logEvent(Constants.TRANS_TYPE_JDBC + ".url", url);

			Assert.notNull(transaction);
		} catch (Throwable e) {
			// TODO Anders 是否要添加cat error log
//			LOGGER.error("failed to create cat transaction", e);
			Cat.logError(e);
			errorLog("failed to create cat transaction", e);
			return invocation.proceed();
		}

		Object object = null;
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
			debugLog("<<<<<<<<<<<<<<<< " + sql, url);
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
	
	private void debugLog(String log, String info) {
		LOGGER.debug(log
				+ " [type:"
				+ Constants.TRANS_TYPE_JDBC
				+ ", info:"
				+ info
				+ ", traceId:"
				+ Cat.getManager().getThreadLocalMessageTree()
						.getParentMessageId() + "]");
	}

	private void errorLog(String log, Throwable e) {
		LOGGER.error(log
				+ " [type:"
				+ Constants.TRANS_TYPE_JDBC
				+ ", traceId:"
				+ Cat.getManager().getThreadLocalMessageTree()
						.getParentMessageId() + "]", e);
	}	

}
