package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.NoncoreWordStrategy;

public interface NoncoreWordStrategyService extends GenericService<NoncoreWordStrategy, Long> {
	public NoncoreWordStrategy getNoncoreWordStrategy();

	public List<Map<String, Object>> getCoreWordRuleListForFE();

	public boolean saveRuleId(Long ruleId, Long loginUser);
}