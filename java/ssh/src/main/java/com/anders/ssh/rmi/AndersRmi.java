package com.anders.ssh.rmi;

import java.util.Map;
import java.util.Set;

public interface AndersRmi {
	Map<Long, String> getRmi();

	/**
	 * 通过synchronized锁定对象，避免RMI同一时间被多次调用，始终只有一个客户端可以调用RMI，单元测试先跑SynRmiTest1，再跑SynRmiTest2
	 */
	public Set<Boolean> synGetRmi(Boolean b);
}
