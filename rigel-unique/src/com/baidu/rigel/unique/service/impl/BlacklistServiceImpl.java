package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.dao.BlacklistDao;
import com.baidu.rigel.unique.service.BlacklistService;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.Utils;

@Service("blacklistService")
public class BlacklistServiceImpl extends GenericSqlMapServiceImpl<Blacklist, Long> implements BlacklistService {

	@Autowired
	private BlacklistDao blacklistDao;

	public List<Long> equalCompanyName(String companyName) {
		if (Utils.isNull(companyName))
			return new ArrayList<Long>();
		return blacklistDao.selectBlacklistIdByCompanyName(companyName);
	}

	public List<Map<String, Object>> containCompanyName(String companyName) {
		if (Utils.isNull(companyName))
			return new ArrayList<Map<String, Object>>();
		return blacklistDao.selectBlacklistIdCompanyNameLikeByCompanyName(Utils.addWildcard(Utils.escapeWildcard(companyName)));
	}

	public List<Long> equalUrl(String url) {
		if (Utils.isNull(url))
			return new ArrayList<Long>();
		return blacklistDao.selectBlacklistIdByUrl(url);
	}

	public Blacklist findById(Long id) {
		if (Utils.isNull(id))
			return null;
		return blacklistDao.findById(id);
	}

	public List<Long> selectDisCreatorIdList() {
		return blacklistDao.selectDisCreatorIdList();
	}

	public List<Blacklist> pageList(String companyName, String url, Long createId, List<Short> srcList, int curPage, int pageSize) {
		if (StringUtils.isBlank(companyName))
			companyName = null;
		else
			companyName = Utils.addWildcard(Utils.escapeWildcard(companyName));

		if (StringUtils.isBlank(url))
			url = null;
		else
			url = Utils.addWildcard(Utils.escapeWildcard(url));

		if (CollectionUtils.isEmpty(srcList))
			srcList = null;

		if (Utils.isLessThanZero(curPage))
			curPage = Constant.ZERO;

		return blacklistDao.pageList(companyName, url, createId, srcList, curPage, pageSize);
	}

	public Long pageCount(String companyName, String url, Long createId, List<Short> srcList) {
		if (StringUtils.isBlank(companyName))
			companyName = null;
		else
			companyName = Utils.addWildcard(Utils.escapeWildcard(companyName));

		if (StringUtils.isBlank(url))
			url = null;
		else
			url = Utils.addWildcard(Utils.escapeWildcard(url));

		if (CollectionUtils.isEmpty(srcList))
			srcList = null;

		return blacklistDao.pageCount(companyName, url, createId, srcList);
	}

	// TODO Anders Zhu : 删除
	// public Blacklist saveOrUpdate(Blacklist blacklist) {
	// if (Utils.isNull(blacklist))
	// return null;
	//
	// if (Utils.isNull(blacklist.getBlacklistId())) {
	// blacklistDao.save(blacklist);
	// } else {
	// Blacklist bl = blacklistDao.findById(blacklist.getBlacklistId());
	// if (Utils.isNull(bl)) {
	// blacklistDao.save(blacklist);
	// } else {
	// blacklistDao.update(blacklist);
	// }
	// }
	// return blacklist;
	// }

	public void deleteBlacklist(Long blacklistId) {
		if (Utils.isNotNull(blacklistId))
			blacklistDao.deleteBlacklist(blacklistId);
	}

	@Override
	public SqlMapDao<Blacklist, Long> getDao() {
		return blacklistDao;
	}
}
