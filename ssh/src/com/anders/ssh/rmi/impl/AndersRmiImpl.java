package com.anders.ssh.rmi.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.anders.ssh.rmi.AndersRmi;

public class AndersRmiImpl implements AndersRmi {
	private Lock lock = new ReentrantLock();

	@Override
	public Map<Long, String> getRmi() {
		Map<Long, String> map = new HashMap<Long, String>();
		map.put(1L, "zhuzhen");
		map.put(2L, "guolili");
		return map;
	}

	@Override
	public Set<Boolean> synGetRmi(Boolean b) {
		Set<Boolean> set = new HashSet<Boolean>();
		set.add(false);

		if (!lock.tryLock())
			return set;

		try {
			// 暂停三分钟
			Thread.sleep(1000 * 60 * 3);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		set.clear();
		set.add(b);
		lock.unlock();

		return set;
	}
}
