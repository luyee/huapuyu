package com.anders.ssh.rmi.impl;

import java.util.HashMap;
import java.util.Map;

import com.anders.ssh.rmi.AndersBurlap;

public class AndersBurlapImpl implements AndersBurlap {
	@Override
	public Map<Long, String> getBurlap() {
		Map<Long, String> map = new HashMap<Long, String>();
		map.put(1L, "zhuzhen");
		map.put(2L, "guolili");
		return map;
	}
}
