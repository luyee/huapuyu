package com.baidu.rigel.unique.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.baidu.rigel.unique.common.BaseActionSupport;
import com.baidu.rigel.unique.service.NoncoreWordStrategyService;
import com.baidu.rigel.unique.utils.Utils;

public class CoreWordRuleAction extends BaseActionSupport {
	private static final Logger logger = Logger.getLogger(CoreWordRuleAction.class);

	private List<Map<String, Object>> coreWordRuleList;

	private List<Long> coreWordRuleSel;

	private NoncoreWordStrategyService noncoreWordStrategyService;

	@Override
	public String execute() throws Exception {
		coreWordRuleList = noncoreWordStrategyService.getCoreWordRuleListForFE();
		return SUCCESS;
	}

	public String modify() throws Exception {
		if (coreWordRuleSel == null) {
			coreWordRuleSel = new ArrayList<Long>();
		}
		Long ruleId = new Long(Utils.IntToBit(coreWordRuleSel));

		// 获得当前登陆用户id
		Long loginUser = null;
		loginUser = userCenterHelper.getUser().getUcid();

		boolean saveFlag = noncoreWordStrategyService.saveRuleId(ruleId, loginUser);
		if (!saveFlag) {
			addFieldError("coreWordRule", "更新失败");
			logger.error("更新失败");
		}

		return execute();
	}

	public List<Map<String, Object>> getCoreWordRuleList() {
		return coreWordRuleList;
	}

	public void setCoreWordRuleList(List<Map<String, Object>> coreWordRuleList) {
		this.coreWordRuleList = coreWordRuleList;
	}

	public List<Long> getCoreWordRuleSel() {
		return coreWordRuleSel;
	}

	public void setCoreWordRuleSel(List<Long> coreWordRuleSel) {
		this.coreWordRuleSel = coreWordRuleSel;
	}

	public NoncoreWordStrategyService getNoncoreWordStrategyService() {
		return noncoreWordStrategyService;
	}

	public void setNoncoreWordStrategyService(NoncoreWordStrategyService noncoreWordStrategyService) {
		this.noncoreWordStrategyService = noncoreWordStrategyService;
	}

}
