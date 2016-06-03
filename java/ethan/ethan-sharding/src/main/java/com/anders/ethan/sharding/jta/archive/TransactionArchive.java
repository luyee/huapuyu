package com.anders.ethan.sharding.jta.archive;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.anders.ethan.sharding.jta.common.TransactionContext;
import com.anders.ethan.sharding.jta.common.TransactionStatus;
import com.anders.ethan.sharding.jta.xa.XidImpl;

public class TransactionArchive {
	protected TransactionStatus transactionStatus;
	protected TransactionContext transactionContext;

	protected final Map<XidImpl, CompensableArchive> xidToNativeSvcMap = new ConcurrentHashMap<XidImpl, CompensableArchive>();
	protected final Map<String, TerminatorArchive> appToTerminatorMap = new ConcurrentHashMap<String, TerminatorArchive>();

	public TransactionContext getTransactionContext() {
		return transactionContext;
	}

	public void setTransactionContext(TransactionContext transactionContext) {
		this.transactionContext = transactionContext;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Map<XidImpl, CompensableArchive> getXidToNativeSvrMap() {
		return xidToNativeSvcMap;
	}

	public Map<String, TerminatorArchive> getAppToTerminatorMap() {
		return appToTerminatorMap;
	}

}
