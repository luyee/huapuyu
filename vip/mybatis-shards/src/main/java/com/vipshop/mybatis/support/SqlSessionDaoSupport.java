package com.vipshop.mybatis.support;

import static org.springframework.util.Assert.notNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;

import com.vipshop.mybatis.SqlSessionFactoryBean;
import com.vipshop.mybatis.SqlSessionTemplate;
import com.vipshop.mybatis.common.ShardParam;
import com.vipshop.mybatis.common.StrategyHolder;
import com.vipshop.mybatis.common.TransactionHolder;
import com.vipshop.mybatis.common.TransactionInfoWrap;
import com.vipshop.mybatis.strategy.NoShardStrategy;
import com.vipshop.mybatis.strategy.ShardStrategy;

public abstract class SqlSessionDaoSupport extends DaoSupport {

	private SqlSessionFactoryBean sqlSessionFactoryBean;

	private Map<DataSource, SqlSessionTemplate> dataSourceMap;

	private SqlSession sqlSession;

	{
		sqlSession = (SqlSession) Proxy.newProxyInstance(SqlSessionDaoSupport.class.getClassLoader(), new Class[] { SqlSession.class }, new SessionHandler());
	}

	@Autowired(required = false)
	public final void setSqlSessionFactory(SqlSessionFactoryBean sqlSessionFactoryBean) {
		this.sqlSessionFactoryBean = sqlSessionFactoryBean;
	}

	public final SqlSession getSqlSession() {
		return sqlSession;
	}

	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
	}

	@Override
	protected void initDao() throws Exception {
		// TODO Anders Zhu 这个可以没有
		// sqlSessionFactoryBean.afterPropertiesSet();

		dataSourceMap = new LinkedHashMap<DataSource, SqlSessionTemplate>();
		dataSourceMap.put(sqlSessionFactoryBean.getDataSource(), new SqlSessionTemplate(sqlSessionFactoryBean.getSqlSessionFactory()));

		Map<String, DataSource> shardDataSources = sqlSessionFactoryBean.getShardDataSourceMap();
		if (MapUtils.isNotEmpty(shardDataSources)) {
			for (Entry<String, DataSource> entry : shardDataSources.entrySet()) {
				SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getShardSqlSessionFactoryMap().get(entry.getKey());
				dataSourceMap.put(entry.getValue(), new SqlSessionTemplate(sqlSessionFactory));
			}
		}
	}

	/**
	 * 此Handler并没有做相关的事务操作，只是将相关的事务信息保存到ThreadLocal中，具体的事务处理还是由TransactionAspectSupportExt处理
	 * @author Anders
	 *
	 */
	private class SessionHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			try {
				DataSource targetDataSource = sqlSessionFactoryBean.getDataSource();

				// args第一个参数为MyBatis statement，第二个参数为方法参数
				if (ArrayUtils.isEmpty(args)) {
					prepareTx(targetDataSource);
					return method.invoke(dataSourceMap.get(sqlSessionFactoryBean.getDataSource()), args);
				}
				if (!(args[0] instanceof String)) {
					prepareTx(targetDataSource);
					return method.invoke(dataSourceMap.get(sqlSessionFactoryBean.getDataSource()), args);
				}
				ShardParam shardParam = (args.length > 1 && args[1] instanceof ShardParam) ? (ShardParam) args[1] : null;
				if (shardParam == null) {
					prepareTx(targetDataSource);
					return method.invoke(dataSourceMap.get(sqlSessionFactoryBean.getDataSource()), args);
				}
				else {
					args[1] = shardParam.getParams();
				}

				String statement;
				String shardStrategyName;
				ShardStrategy shardStrategy;

				statement = (String) args[0];
				shardStrategyName = shardParam.getName();

				shardStrategy = sqlSessionFactoryBean.getShardStrategyMap().get(shardStrategyName);
				if (shardStrategy == null) {
					shardStrategy = NoShardStrategy.INSTANCE;
				}

				Configuration configuration = sqlSessionFactoryBean.getSqlSessionFactory().getConfiguration();
				MappedStatement mappedStatement = configuration.getMappedStatement(statement);
				BoundSql boundSql = mappedStatement.getBoundSql(wrapCollection(shardParam.getParams()));

				shardStrategy.setDataSource(sqlSessionFactoryBean.getDataSource());
				shardStrategy.setShardDataSources(sqlSessionFactoryBean.getShardDataSourceMap());
				shardStrategy.setShardParam(shardParam);
				shardStrategy.setSql(boundSql.getSql());

				StrategyHolder.setShardStrategy(shardStrategy);

				targetDataSource = shardStrategy.getTargetDataSource();
				SqlSessionTemplate sqlSessionTemplate = null;
				if (targetDataSource == null || (sqlSessionTemplate = dataSourceMap.get(targetDataSource)) == null) {
					targetDataSource = sqlSessionFactoryBean.getDataSource();
					sqlSessionTemplate = dataSourceMap.get(targetDataSource);
				}

				prepareTx(targetDataSource);

				return method.invoke(sqlSessionTemplate, args);
			}
			finally {
				StrategyHolder.removeShardStrategy();
			}
		}

		/**
		 * 准备事务
		 * 
		 * @param dataSource
		 */
		private void prepareTx(DataSource dataSource) {
			TransactionHolder.setDataSource(dataSource);

			TransactionInfoWrap transactionInfoWrap = TransactionHolder.getTransactionInfoWrap();
			if (transactionInfoWrap != null) {
				TransactionAttribute transactionAttribute = transactionInfoWrap.getTransactionAttribute();
				if (transactionAttribute != null) {
					createTxIfAbsent(dataSource, transactionInfoWrap);
				}
			}
		}

		/**
		 * 不存在则创建事务
		 * 
		 * @param dataSource
		 * @param transactionInfoWrap
		 */
		private void createTxIfAbsent(DataSource dataSource, TransactionInfoWrap transactionInfoWrap) {
			Map<DataSource, LinkedList<TransactionInfoWrap>> ds2TxTree = TransactionHolder.getDataSource2TxTree();
			if (ds2TxTree == null || !ds2TxTree.containsKey(dataSource)) {
				createTx(dataSource, transactionInfoWrap);
			}
		}

		/**
		 * 创建事务
		 * 
		 * @param targetDS
		 * @param txInfo
		 */
		private void createTx(DataSource dataSource, TransactionInfoWrap transactionInfoWrap) {
			TransactionStatus transactionStatus = transactionInfoWrap.getTransactionManager().getTransaction(transactionInfoWrap.getTransactionAttribute());
			// txStatus = new TransactionStatusWrap((DefaultTransactionStatus)
			// txStatus);
			TransactionHolder.addStatus2DataSource(transactionStatus, dataSource);

			TransactionInfoWrap txInfoCopy = transactionInfoWrap.newCopy();
			txInfoCopy.setTransactionStatus(transactionStatus);

			TransactionHolder.addTxInfo2Tree(dataSource, txInfoCopy);
		}

		private Object wrapCollection(final Object object) {
			if (object instanceof List) {
				return new HashMap<String, Object>() {
					private static final long serialVersionUID = -2533602760878803345L;
					{
						put("list", object);
					}
				};
			}
			else if (object != null && object.getClass().isArray()) {
				return new HashMap<String, Object>() {
					private static final long serialVersionUID = 8371167260656531195L;
					{
						put("array", object);
					}
				};
			}
			return object;
		}
	}
}
