package com.anders.ssh.rmi.impl;

import java.util.HashMap;
import java.util.Map;

import com.anders.ssh.rmi.AndersHessian;

public class AndersHessianImpl implements AndersHessian {
	@Override
	public Map<Long, String> getHessian() {
		Map<Long, String> map = new HashMap<Long, String>();
		map.put(1L, "zhuzhen");
		map.put(2L, "guolili");
		return map;
	}
}
