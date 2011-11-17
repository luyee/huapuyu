package com.anders.ssh.rmi.impl;

import java.util.HashMap;
import java.util.Map;

import com.anders.ssh.rmi.AndersRmi;

public class AndersRmiImpl implements AndersRmi {
	@Override
	public Map<Long, String> getUserInfo() {
		Map<Long, String> map = new HashMap<Long, String>();
		map.put(1L, "zhuzhen");
		map.put(2L, "guolili");
		return map;
	}
}
