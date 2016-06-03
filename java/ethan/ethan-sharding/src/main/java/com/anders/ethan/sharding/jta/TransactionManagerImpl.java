package com.anders.ethan.sharding.jta;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import com.anders.ethan.sharding.jta.common.AssociatedContext;
import com.anders.ethan.sharding.jta.common.PropagationKey;
import com.anders.ethan.sharding.jta.common.TerminalKey;
import com.anders.ethan.sharding.jta.common.TransactionContext;
import com.anders.ethan.sharding.jta.common.TransactionStatus;
import com.anders.ethan.sharding.jta.xa.XidFactoryImpl;
import com.anders.ethan.sharding.jta.xa.XidImpl;

public class TransactionManagerImpl implements TransactionManager {
	
	private final static ThreadLocal<AssociatedContext> TRX_CONTEXT = new ThreadLocal<AssociatedContext>();
	
	private XidFactoryImpl xidFactory;
	private PropagationKey instanceKey;
	private int transactionTimeout = 5 * 60;

	@Override
	public void begin() throws NotSupportedException, SystemException {
		System.out.println(this + "begin");
		Transaction transaction = getCurrentTrx(); 
		if (transaction == null) {
			beginDisTrx();
		} else {
			beginLocalTrx();
		}
	}

	@Override
	public void commit() throws RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SecurityException,
			IllegalStateException, SystemException {
		System.out.println(this + "commit");
	}

	@Override
	public int getStatus() throws SystemException {
		System.out.println(this + "getStatus");
		return 6;
	}

	@Override
	public Transaction getTransaction() throws SystemException {
		System.out.println(this + "getTransaction");
		return null;
	}

	@Override
	public void resume(Transaction tobj) throws InvalidTransactionException,
			IllegalStateException, SystemException {
		System.out.println(this + "resume");
	}

	@Override
	public void rollback() throws IllegalStateException, SecurityException,
			SystemException {
		System.out.println(this + "rollback");
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		System.out.println(this + "setRollbackOnly");
	}

	@Override
	public void setTransactionTimeout(int seconds) throws SystemException {
		System.out.println(this + "setTransactionTimeout");
	}

	@Override
	public Transaction suspend() throws SystemException {
		System.out.println(this + "suspend");
		return null;
	}

	private void beginLocalTrx() {
		
	}
	
	private void beginDisTrx() {
		int timeoutSeconds = this.transactionTimeout;
		TransactionContext transactionContext = new TransactionContext();
		transactionContext.setCoordinator(true);
		long createdTime = System.currentTimeMillis();
		long expiredTime = createdTime + (timeoutSeconds * 1000L);
		transactionContext.setCreatedTime(createdTime);
		transactionContext.setExpiredTime(expiredTime);
		transactionContext.setTerminalKey(this.getTerminalKey());
		XidImpl globalXid = this.xidFactory.createGlobalXid();
		// transactionContext.setGlobalXid(globalXid);
		transactionContext.setCreationXid(globalXid);
		transactionContext.setCurrentXid(globalXid);
		
		TransactionImpl transaction = new TransactionImpl();
//		transaction.setTransactionStatistic(this.transactionStatistic);

		// transaction.setTransactionTimeout(timeoutSeconds);
		transaction.setTransactionContext(transactionContext);
		transaction.setTransactionStatus(new TransactionStatus());
		transaction.setTransactionManager(this);
//		transaction.setTransactionLogger(this.transactionRepository.getTransactionLogger());

		transaction.associateInternalTransaction();

		AssociatedContext actx = new AssociatedContext();
		actx.setTransaction(transaction);
//		actx.setThread(Thread.currentThread());
		

//		this.threadToTxnMap.put(Thread.currentThread(), actx);
		TRX_CONTEXT.set(actx);
//		this.transactionRepository.putTransaction(transactionContext.getGlobalXid(), transaction);
//		this.transactionStatistic.fireBeginTransaction(transaction);

//		TransactionLogger transactionLogger = this.transactionRepository.getTransactionLogger();
//		transactionLogger.beginTransaction(transaction);

//		AbstractSynchronization sync = this.threadToSynMap.get(Thread.currentThread());
//		if (sync != null) {
//			try {
//				sync.afterCreation(transactionContext.getCurrentXid());
//			} finally {
//				this.unRegisterSynchronization(sync);
//			}
//		}
	}
	
	private TransactionImpl getCurrentTrx() {
		AssociatedContext ac = TRX_CONTEXT.get();
		return ac == null ? null : ac.getTransaction();
	}
	
	public TerminalKey getTerminalKey() {
		return this.xidFactory.getTerminalKey();
	}
}
