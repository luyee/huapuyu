package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

public interface AuditService {
	public List<Map<String, Object>> listMatchCustUrl(String url);

	public List<Map<String, Object>> listPreMatchCustUrl(String url, int count);

	public List<Map<String, Object>> listMatchDomain(String domain);

	public List<Map<String, Object>> listPreMatchDomain(String domain);

	public List<Map<String, Object>> listMatchPhoneContactMap(String phoneAreaCode, String phoneNumber);

	public List<Long> listMatchPhoneContact(String fullPhone);
}
