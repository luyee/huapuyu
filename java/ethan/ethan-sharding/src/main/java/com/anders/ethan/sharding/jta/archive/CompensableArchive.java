package com.anders.ethan.sharding.jta.archive;

import java.io.Serializable;

import com.anders.ethan.sharding.jta.compensable.Compensable;
import com.anders.ethan.sharding.jta.util.CommonUtils;
import com.anders.ethan.sharding.jta.xa.XidImpl;

public class CompensableArchive {
	public boolean launchSvc;
	public XidImpl branchXid;
	public Compensable<Serializable> service;
	public Serializable variable;
	public boolean tryCommitted;
	public boolean confirmed;
	public boolean cancelled;
	public boolean committed;
	public boolean rolledback;

	public int hashCode() {
		int hash = 23;
		hash += 29 * (this.branchXid == null ? 0 : this.branchXid.hashCode());
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (this.getClass().equals(obj.getClass()) == false) {
			return false;
		}
		CompensableArchive that = (CompensableArchive) obj;
		return CommonUtils.equals(this.branchXid, that.branchXid);
	}
}
