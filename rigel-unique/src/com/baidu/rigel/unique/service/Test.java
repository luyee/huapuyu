package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

public interface Test {
	public List<Map<String, Object>> containContactPhone(String phoneAreaCode, String phoneNumber, int count);

	public List<Map<String, Object>> equalContactPhone(String phoneAreaCode, String phoneNumber, int count);

	public boolean validateCustName(String name);
}
