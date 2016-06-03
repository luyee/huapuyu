package com.anders.ethan.sharding.jta.remote;

import java.rmi.RemoteException;

public interface Cleanupable {
	public void cleanup() throws RemoteException;
}
