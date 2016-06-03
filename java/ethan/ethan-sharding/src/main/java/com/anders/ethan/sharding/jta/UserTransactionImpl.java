package com.anders.ethan.sharding.jta;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class UserTransactionImpl implements UserTransaction {
	
	private transient TransactionManager transactionManager;

	@Override
	public void begin() throws NotSupportedException, SystemException {
		this.transactionManager.begin();
		System.out.println(this + "begin");
	}

	@Override
	public void commit() throws RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SecurityException,
			IllegalStateException, SystemException {
		System.out.println(this + "commit");
		this.transactionManager.commit();
	}

	@Override
	public void rollback() throws IllegalStateException, SecurityException,
			SystemException {
		System.out.println(this + "rollback");
		this.transactionManager.rollback();
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		System.out.println(this + "setRollbackOnly");
		this.transactionManager.setRollbackOnly();
	}

	@Override
	public int getStatus() throws SystemException {
		System.out.println(this + "getStatus");
		return this.transactionManager.getStatus();
	}

	@Override
	public void setTransactionTimeout(int seconds) throws SystemException {
		System.out.println(this + "setTransactionTimeout");
		this.transactionManager.setTransactionTimeout(seconds);
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	

}
