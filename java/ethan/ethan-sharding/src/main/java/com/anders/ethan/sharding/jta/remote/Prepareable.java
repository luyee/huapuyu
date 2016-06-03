package com.anders.ethan.sharding.jta.remote;

import java.rmi.RemoteException;

import javax.transaction.SystemException;

import com.anders.ethan.sharding.jta.exception.NoSuchTransactionException;

public interface Prepareable {

	public void prepare() throws SystemException, RemoteException, NoSuchTransactionException;

}
