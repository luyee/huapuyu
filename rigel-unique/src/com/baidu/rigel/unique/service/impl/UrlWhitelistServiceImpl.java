package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.UrlWhitelist;
import com.baidu.rigel.unique.dao.UrlWhitelistDao;
import com.baidu.rigel.unique.service.UrlWhitelistService;
import com.baidu.rigel.unique.utils.PeriodType;
import com.baidu.rigel.unique.utils.Utils;

@Service("urlWhitelistService")
public class UrlWhitelistServiceImpl extends GenericSqlMapServiceImpl<UrlWhitelist, Long> implements UrlWhitelistService {

	@Autowired
	private UrlWhitelistDao urlWhitelistDao;

	public boolean isDomainAndPosIdsExist(String domain, Long[] posIds) {
		if (StringUtils.isBlank(domain) || ArrayUtils.isEmpty(posIds))
			return false;
		return Utils.isGreaterThanZero(urlWhitelistDao.selectCountByDomainPosIdList(domain, Arrays.asList(posIds)));
	}

	public Long pageCount(Long posId, String domain, Short cType, Long updateId) {
		if (Utils.isNull(posId))
			return NumberUtils.LONG_ZERO;

		if (StringUtils.isBlank(domain))
			domain = null;

		if (Utils.isNotNullAndEqualToMinusOne(cType))
			cType = null;

		if (Utils.isNotNullAndEqualToOne(updateId))
			updateId = null;

		return urlWhitelistDao.pageCount(posId, domain, cType, updateId);
	}

	public List<Map<String, Object>> pageList(Long posId, String domain, Short cType, Long updateId, int curPage, int pageSize) {
		if (Utils.isNull(posId))
			return new ArrayList<Map<String, Object>>(0);

		if (StringUtils.isBlank(domain))
			domain = null;

		if (Utils.isNotNullAndEqualToMinusOne(cType))
			cType = null;

		if (Utils.isNotNullAndEqualToOne(updateId))
			updateId = null;

		if (Utils.isLessThanZero(curPage))
			curPage = NumberUtils.INTEGER_ZERO;

		return urlWhitelistDao.pageList(posId, domain, cType, updateId, curPage, pageSize);
	}

	public List<Long> selectDisUpdateIdByPosId(Long posId) {
		if (Utils.isNull(posId))
			return new ArrayList<Long>(0);

		return urlWhitelistDao.selectDisUpdateIdByPosId(posId);
	}

	public boolean isDomainAndPosIdExist(String domain, Long posId) {
		if (StringUtils.isBlank(domain) || Utils.isNull(posId))
			return false;
		return Utils.isGreaterThanZero(urlWhitelistDao.selectCountByDomainPosId(domain, posId));
	}

	// TODO Anders Zhu : 重构
	public void addUrlWhitelist(UrlWhitelist urlWhitelist, Long ucid) {
		if (Utils.isNull(urlWhitelist) || Utils.isNull(ucid))
			return;

		urlWhitelist.setCreatorId(ucid);
		urlWhitelist.setCreateTime(new Date());
		urlWhitelist.setUpdateId(ucid);
		urlWhitelist.setUpdateTime(new Date());
		urlWhitelist.setDelFlag((byte) 0);
		// 设置截止日期为5天后
		if (urlWhitelist.getcType().shortValue() == PeriodType.SHORT.getValue()) {
			Calendar invalidate = Calendar.getInstance();
			invalidate.add(Calendar.DATE, 5);
			urlWhitelist.setInvalidate(invalidate.getTime());
		}
		saveOrUpdate(urlWhitelist);
	}

	// TODO Anders Zhu : 重构
	public void updateUrlWhitelist(UrlWhitelist urlWhitelist, Long ucid) {
		if (urlWhitelist == null || ucid == null)
			return;

		Date now = new Date();
		urlWhitelist.setUpdateId(ucid);
		urlWhitelist.setUpdateTime(now);
		// 设置截止日期为5天后
		if (urlWhitelist.getcType().shortValue() == PeriodType.SHORT.getValue()) {
			Calendar invalidate = Calendar.getInstance();
			invalidate.add(Calendar.DATE, 5);
			urlWhitelist.setInvalidate(invalidate.getTime());
		} else {
			urlWhitelist.setInvalidate(null);
		}
		saveOrUpdate(urlWhitelist);
	}

	public void deleteUrlWhitelist(UrlWhitelist urlWhitelist, Long ucid) {
		if (urlWhitelist == null || ucid == null)
			return;

		Date now = new Date();
		urlWhitelist.setUpdateId(ucid);
		urlWhitelist.setUpdateTime(now);
		urlWhitelist.setDelFlag((byte) 1);
		saveOrUpdate(urlWhitelist);
	}

	@Override
	public SqlMapDao<UrlWhitelist, Long> getDao() {
		return urlWhitelistDao;
	}
}
