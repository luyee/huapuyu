package com.anders.ethan.sharding.jta.remote;

import com.anders.ethan.sharding.jta.common.TerminalKey;

public interface RemoteTerminator extends Prepareable, Committable, Rollbackable, Cleanupable {

	public TerminalKey getTerminalKey();

	public int hashCode();

	public boolean equals(Object obj);

}
