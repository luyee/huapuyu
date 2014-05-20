package com.vipshop.mybatis.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionHolder;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public final class SqlSessionUtils {

	private static final Logger logger = LoggerFactory.getLogger(SqlSessionUtils.class);

	private SqlSessionUtils() {
	}

	public static SqlSession getSqlSession(SqlSessionFactory sessionFactory) {
		ExecutorType executorType = sessionFactory.getConfiguration().getDefaultExecutorType();
		return getSqlSession(sessionFactory, executorType, null);
	}

	public static SqlSession getSqlSession(SqlSessionFactory sessionFactory, ExecutorType executorType, PersistenceExceptionTranslator exceptionTranslator) {

		Assert.notNull(sessionFactory, "No SqlSessionFactory specified");
		Assert.notNull(executorType, "No ExecutorType specified");

		SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

		if (holder != null && holder.isSynchronizedWithTransaction()) {
			if (holder.getExecutorType() != executorType) {
				throw new TransientDataAccessResourceException("Cannot change the ExecutorType when there is an existing transaction");
			}

			holder.requested();

			if (logger.isDebugEnabled()) {
				logger.debug("Fetched SqlSession [" + holder.getSqlSession() + "] from current transaction");
			}

			return holder.getSqlSession();
		}

		DataSource dataSource = sessionFactory.getConfiguration().getEnvironment().getDataSource();

		boolean transactionAware = (dataSource instanceof TransactionAwareDataSourceProxy);
		Connection conn;
		try {
			conn = transactionAware ? dataSource.getConnection() : DataSourceUtils.getConnection(dataSource);
		}
		catch (SQLException e) {
			throw new CannotGetJdbcConnectionException("Could not get JDBC Connection for SqlSession", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Creating SqlSession with JDBC Connection [" + conn + "]");
		}

		SqlSession session = sessionFactory.openSession(executorType, conn);

		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			if (!(sessionFactory.getConfiguration().getEnvironment().getTransactionFactory() instanceof SpringManagedTransactionFactory) && DataSourceUtils.isConnectionTransactional(conn, dataSource)) {
				throw new TransientDataAccessResourceException("SqlSessionFactory must be using a SpringManagedTransactionFactory in order to use Spring transaction synchronization");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Registering transaction synchronization for SqlSession [" + session + "]");
			}
			holder = new SqlSessionHolder(session, executorType, exceptionTranslator);
			TransactionSynchronizationManager.bindResource(sessionFactory, holder);
			TransactionSynchronizationManager.registerSynchronization(new SqlSessionSynchronization(holder, sessionFactory));
			holder.setSynchronizedWithTransaction(true);
			holder.requested();
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("SqlSession [" + session + "] was not registered for synchronization because synchronization is not active");
			}
		}

		return session;
	}

	public static void closeSqlSession(SqlSession session, SqlSessionFactory sessionFactory) {

		Assert.notNull(session, "No SqlSession specified");
		Assert.notNull(sessionFactory, "No SqlSessionFactory specified");

		SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		if ((holder != null) && (holder.getSqlSession() == session)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Releasing transactional SqlSession [" + session + "]");
			}
			holder.released();
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Closing no transactional SqlSession [" + session + "]");
			}
			session.close();
		}
	}

	public static boolean isSqlSessionTransactional(SqlSession session, SqlSessionFactory sessionFactory) {
		Assert.notNull(session, "No SqlSession specified");
		Assert.notNull(sessionFactory, "No SqlSessionFactory specified");

		SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);

		return (holder != null) && (holder.getSqlSession() == session);
	}

	private static final class SqlSessionSynchronization extends TransactionSynchronizationAdapter {

		private final SqlSessionHolder holder;

		private final SqlSessionFactory sessionFactory;

		public SqlSessionSynchronization(SqlSessionHolder holder, SqlSessionFactory sessionFactory) {
			Assert.notNull(holder, "Parameter 'holder' must be not null");
			Assert.notNull(sessionFactory, "Parameter 'sessionFactory' must be not null");

			this.holder = holder;
			this.sessionFactory = sessionFactory;
		}

		@Override
		public int getOrder() {
			return DataSourceUtils.CONNECTION_SYNCHRONIZATION_ORDER - 1;
		}

		@Override
		public void suspend() {
			TransactionSynchronizationManager.unbindResource(this.sessionFactory);
		}

		@Override
		public void resume() {
			TransactionSynchronizationManager.bindResource(this.sessionFactory, this.holder);
		}

		@Override
		public void beforeCommit(boolean readOnly) {
			if (TransactionSynchronizationManager.isActualTransactionActive()) {
				try {
					if (logger.isDebugEnabled()) {
						logger.debug("Transaction synchronization committing SqlSession [" + this.holder.getSqlSession() + "]");
					}
					this.holder.getSqlSession().commit();
				}
				catch (PersistenceException p) {
					if (this.holder.getPersistenceExceptionTranslator() != null) {
						DataAccessException translated = this.holder.getPersistenceExceptionTranslator().translateExceptionIfPossible(p);
						if (translated != null) {
							throw translated;
						}
					}
					throw p;
				}
			}
		}

		@Override
		public void afterCompletion(int status) {
			if (!this.holder.isOpen()) {
				TransactionSynchronizationManager.unbindResource(this.sessionFactory);
				try {
					if (logger.isDebugEnabled()) {
						logger.debug("Transaction synchronization closing SqlSession [" + this.holder.getSqlSession() + "]");
					}
					this.holder.getSqlSession().close();
				}
				finally {
					this.holder.reset();
				}
			}
		}
	}

}
