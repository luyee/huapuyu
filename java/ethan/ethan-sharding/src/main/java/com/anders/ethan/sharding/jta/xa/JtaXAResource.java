package com.anders.ethan.sharding.jta.xa;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class JtaXAResource implements XAResource {

	@Override
	public void commit(Xid arg0, boolean arg1) throws XAException {
		System.out.println(this + "commit");
	}

	@Override
	public void end(Xid arg0, int arg1) throws XAException {
		System.out.println(this + "end");
	}

	@Override
	public void forget(Xid arg0) throws XAException {
		System.out.println(this + "forget");
	}

	@Override
	public int getTransactionTimeout() throws XAException {
		System.out.println(this + "getTransactionTimeout");
		return 0;
	}

	@Override
	public boolean isSameRM(XAResource arg0) throws XAException {
		System.out.println(this + "isSameRM");
		return false;
	}

	@Override
	public int prepare(Xid arg0) throws XAException {
		System.out.println(this + "prepare");
		return 0;
	}

	@Override
	public Xid[] recover(int arg0) throws XAException {
		System.out.println(this + "recover");
		return null;
	}

	@Override
	public void rollback(Xid arg0) throws XAException {
		System.out.println(this + "rollback");
	}

	@Override
	public boolean setTransactionTimeout(int arg0) throws XAException {
		System.out.println(this + "setTransactionTimeout");
		return false;
	}

	@Override
	public void start(Xid arg0, int arg1) throws XAException {
		System.out.println(this + "start");
	}

}
