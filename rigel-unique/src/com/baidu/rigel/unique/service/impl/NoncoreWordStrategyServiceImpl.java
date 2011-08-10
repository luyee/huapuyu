package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.NoncoreWordStrategy;
import com.baidu.rigel.unique.common.Constant;
import com.baidu.rigel.unique.dao.NoncoreWordStrategyDao;
import com.baidu.rigel.unique.service.NoncoreWordStrategyService;
import com.baidu.rigel.unique.utils.Utils;

@Service("noncoreWordStrategyService")
public class NoncoreWordStrategyServiceImpl extends GenericSqlMapServiceImpl<NoncoreWordStrategy, Long> implements NoncoreWordStrategyService {
	@Resource(name = "noncoreWordStrategyDao")
	private NoncoreWordStrategyDao noncoreWordStrategyDao;

	@Override
	public SqlMapDao<NoncoreWordStrategy, Long> getDao() {
		return noncoreWordStrategyDao;
	}

	// TODO Anders Zhu ： 需要重构
	public NoncoreWordStrategy getNoncoreWordStrategy() {
		NoncoreWordStrategy noncoreWordStrategy = null;
		List<NoncoreWordStrategy> noncoreWordStrategyList = noncoreWordStrategyDao.findAll();
		if (CollectionUtils.isEmpty(noncoreWordStrategyList))
			return new NoncoreWordStrategy();
		noncoreWordStrategy = noncoreWordStrategyList.get(0);
		if (Utils.isNull(noncoreWordStrategy))
			return new NoncoreWordStrategy();
		else
			return noncoreWordStrategy;
	}

	private void buildCoreWordMap(Long coreWordRuleId, Map<Long, Object> coreWordRuleMap) {
		List<Long> coreWordRuleList = null;
		// 将位表示转化为数值表示
		if (coreWordRuleId != null) {
			coreWordRuleList = bitToInt(coreWordRuleId.intValue());
		}

		if (coreWordRuleList != null) {
			for (Long cwr : coreWordRuleList) {
				coreWordRuleMap.put(cwr, cwr);
			}
		}
	}

	private List<Long> bitToInt(int intValue) {
		List<Long> ret = new ArrayList<Long>();
		int i = 0;
		while (true) {
			if ((Double.valueOf(Math.pow(2, i)).intValue() | intValue) == intValue) {
				ret.add(Long.valueOf(i));
			} else if (Double.valueOf(Math.pow(2, i)).intValue() > intValue) {
				break;
			}
			i++;
		}
		return ret;
	}

	public List<Map<String, Object>> getCoreWordRuleListForFE() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		// 获取规则id
		NoncoreWordStrategy nws = getNoncoreWordStrategy();
		Long coreWordRuleId = null;
		if (nws != null) {
			coreWordRuleId = nws.getEnableFilter();
		}

		Map<Long, Object> coreWordRuleMap = new HashMap<Long, Object>();
		// 转化位id 填充到map中
		buildCoreWordMap(coreWordRuleId, coreWordRuleMap);

		// 填充FE所需页面展示List
		Map<String, Object> tmpMap;
		Boolean isChecked;
		int i = 0;
		// 数字
		isChecked = false;
		tmpMap = new HashMap<String, Object>();
		tmpMap.put(Constant.ID, Constant.CORE_WORD_RULE_TYPE_NUMBER);
		tmpMap.put(Constant.NAME, Constant.CORE_WORD_RULE_TYPE_NUMBER_NAME);
		if (coreWordRuleMap.get(Constant.CORE_WORD_RULE_TYPE_NUMBER) != null) {
			isChecked = true;
		}
		tmpMap.put(Constant.SELECT, isChecked);
		result.add(i++, tmpMap);

		// 字母
		isChecked = false;
		tmpMap = new HashMap<String, Object>();
		tmpMap.put(Constant.ID, Constant.CORE_WORD_RULE_TYPE_CHARACTER);
		tmpMap.put(Constant.NAME, Constant.CORE_WORD_RULE_TYPE_CHARACTER_NAME);
		if (coreWordRuleMap.get(Constant.CORE_WORD_RULE_TYPE_CHARACTER) != null) {
			isChecked = true;
		}
		tmpMap.put(Constant.SELECT, isChecked);
		result.add(i++, tmpMap);

		// 数字
		isChecked = false;
		tmpMap = new HashMap<String, Object>();
		tmpMap.put(Constant.ID, Constant.CORE_WORD_RULE_TYPE_WORD);
		tmpMap.put(Constant.NAME, Constant.CORE_WORD_RULE_TYPE_WORD_NAME);
		if (coreWordRuleMap.get(Constant.CORE_WORD_RULE_TYPE_WORD) != null) {
			isChecked = true;
		}
		tmpMap.put(Constant.SELECT, isChecked);
		result.add(i++, tmpMap);

		return result;
	}

	public boolean saveRuleId(Long ruleId, Long loginUser) {
		try {
			if (ruleId == null || loginUser == null) {
				return false;
			}
			NoncoreWordStrategy nws = getNoncoreWordStrategy();
			if (nws == null) {
				nws = new NoncoreWordStrategy();
			}
			if (nws != null) {
				nws.setEnableFilter(ruleId);
				nws.setUpdateId(loginUser);
				nws.setUpdateTime(new Date());
				saveOrUpdate(nws);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}