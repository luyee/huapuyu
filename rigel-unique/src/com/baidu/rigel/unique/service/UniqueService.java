package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

public interface UniqueService {
	public List<Map<String, Object>> containContactPhone(String phoneAreaCode, String phoneNumber, int count);

	public List<Map<String, Object>> equalContactPhone(String phoneAreaCode, String phoneNumber, int count);

	public List<Map<String, Object>> containCustNameStartFromBlacklist(String custName, String custBranchName, int count);

	public List<Map<String, Object>> containCustNameStartFromSale(String custName, String custBranchName, int count);

	public List<Map<String, Object>> equalCustNameStartFromBlacklist(String custFullName);

	public List<Map<String, Object>> equalCustNameStartFromSale(String custFullName);

	public List<Map<String, Object>> containCustUrl(String custUrl, int count);

	public List<Map<String, Object>> equalCustUrl(String custUrl, int count);

	public boolean validateTitle(String name);
}
