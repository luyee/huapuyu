package com.vipshop.mybatis.spring;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * 事务信息包装类
 * 
 * @author Anders
 * 
 */
public class TransactionInfoWrap {

	private final PlatformTransactionManager transactionManager;

	private final TransactionAttribute transactionAttribute;

	private final String joinpointIdentification;

	private TransactionStatus transactionStatus;

	public TransactionInfoWrap(PlatformTransactionManager transactionManager, TransactionAttribute transactionAttribute, String joinpointIdentification) {
		this.transactionManager = transactionManager;
		this.transactionAttribute = transactionAttribute;
		this.joinpointIdentification = joinpointIdentification;
	}

	public PlatformTransactionManager getTransactionManager() {
		return this.transactionManager;
	}

	public TransactionAttribute getTransactionAttribute() {
		return this.transactionAttribute;
	}

	public String getJoinpointIdentification() {
		return this.joinpointIdentification;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public TransactionStatus getTransactionStatus() {
		return this.transactionStatus;
	}

	public boolean hasTransaction() {
		return this.transactionStatus != null;
	}

	@Override
	public String toString() {
		return this.transactionAttribute.toString();
	}

	public TransactionInfoWrap newCopy() {
		return new TransactionInfoWrap(this.transactionManager, this.transactionAttribute, this.joinpointIdentification);
	}
}
