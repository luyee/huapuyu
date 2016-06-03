package com.anders.ethan.sharding.jta;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import com.anders.ethan.sharding.jta.archive.TransactionArchive;
import com.anders.ethan.sharding.jta.xa.XidImpl;

public class TransactionImpl extends TransactionArchive implements Transaction {

	private TransactionManagerImpl transactionManager;
	
	@Override
	public void commit() throws RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SecurityException,
			IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean delistResource(XAResource xaRes, int flag)
			throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enlistResource(XAResource xaRes) throws RollbackException,
			IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getStatus() throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void registerSynchronization(Synchronization sync)
			throws RollbackException, IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}
	
	public UserTransactionImpl associateInternalTransaction() {
//		XidImpl branchXid = this.transactionContext.getCurrentXid();
//		UserTransactionImpl internal = new UserTransactionImpl(this.transactionContext);
//		internal.setXidFactory(this.transactionManager.getXidFactory());
//		internal.initialize();
//		this.internalTxnMap.put(branchXid, internal);
//		return internal;
		return null;
	}

	public TransactionManagerImpl getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManagerImpl transactionManager) {
		this.transactionManager = transactionManager;
	}

}
