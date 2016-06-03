package com.anders.ethan.sharding.jta.archive;

import com.anders.ethan.sharding.jta.remote.RemoteTerminator;
import com.anders.ethan.sharding.jta.util.CommonUtils;

public class TerminatorArchive {
	public RemoteTerminator terminator;
	public boolean prepared;
	public boolean committed;
	public boolean rolledback;
	public boolean cleanup;

	public int hashCode() {
		int hash = 23;
		hash += 29 * (this.terminator == null ? 0 : this.terminator.hashCode());
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (this.getClass().equals(obj.getClass()) == false) {
			return false;
		}
		TerminatorArchive that = (TerminatorArchive) obj;
		return CommonUtils.equals(this.terminator, that.terminator);
	}

}
