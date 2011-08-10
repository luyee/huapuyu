package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.service.BlacklistPhoneService;
import com.baidu.rigel.unique.service.BlacklistService;
import com.baidu.rigel.unique.service.BlacklistVoService;
import com.baidu.rigel.unique.utils.Utils;
import com.baidu.rigel.unique.vo.BlacklistVo;

@Service("blacklistVoService")
public class BlacklistVoServiceImpl implements BlacklistVoService {

	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private BlacklistPhoneService blacklistPhoneService;

	public List<BlacklistVo> pageList(String companyName, String url, Long createId, List<Short> srcList, int curPage, int pageSize) {
		List<Blacklist> blacklistList = blacklistService.pageList(companyName, url, createId, srcList, curPage, pageSize);
		if (CollectionUtils.isNotEmpty(blacklistList)) {
			List<BlacklistVo> blacklistVoList = new LinkedList<BlacklistVo>();
			for (Blacklist blacklist : blacklistList) {
				List<BlacklistPhone> blacklistPhoneList = blacklistPhoneService.selectBlacklistPhoneByBlacklistId(blacklist.getBlacklistId());

				BlacklistVo blacklistVo = new BlacklistVo();
				blacklistVo.setBlacklist(blacklist);
				blacklistVo.setBlacklistPhoneList(blacklistPhoneList);
				blacklistVoList.add(blacklistVo);
			}
			return blacklistVoList;
		}
		return new ArrayList<BlacklistVo>(0);
	}

	public Long pageCount(String companyName, String url, Long createId, List<Short> srcList) {
		return blacklistService.pageCount(companyName, url, createId, srcList);
	}

	public void delete(Long blacklistId) {
		Blacklist blacklist = blacklistService.findById(blacklistId);
		if (Utils.isNotNull(blacklist)) {
			List<BlacklistPhone> blacklistPhoneList = blacklistPhoneService.selectBlacklistPhoneByBlacklistId(blacklistId);
			// TODO Anders Zhu ： 优化，一次性update，不要分步update
			for (BlacklistPhone blacklistPhone : blacklistPhoneList) {
				blacklistPhoneService.deleteBlacklistPhone(blacklistPhone.getId());
			}
			blacklistService.deleteBlacklist(blacklistId);
		}
	}

	public BlacklistVo findBlacklistVo(Long blacklistId) {
		Blacklist blacklist = blacklistService.findById(blacklistId);
		if (Utils.isNotNull(blacklist)) {
			List<BlacklistPhone> blacklistPhoneList = blacklistPhoneService.selectBlacklistPhoneByBlacklistId(blacklist.getBlacklistId());
			BlacklistVo blacklistVo = new BlacklistVo();
			blacklistVo.setBlacklist(blacklist);
			blacklistVo.setBlacklistPhoneList(blacklistPhoneList);
			return blacklistVo;
		}
		return null;
	}
}
