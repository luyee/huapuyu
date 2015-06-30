package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.service.tinyse.data.SaleData;
import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.dao.SeasonCustListDao;
import com.baidu.rigel.unique.service.SeasonCustListService;
import com.baidu.rigel.unique.tinyse.TinyseMgr;
import com.baidu.rigel.unique.utils.Utils;

@Service("seasonCustListService")
public class SeasonCustListServiceImpl extends GenericSqlMapServiceImpl<SeasonCustList, Long> implements SeasonCustListService {

	@Autowired
	private SeasonCustListDao seasonCustListDao;
	@Autowired
	private TinyseMgr tinyseMgr;

	public List<SeasonCustList> equalUrl(String url, List<Long> posIdList) {
		if (CollectionUtils.isEmpty(posIdList))
			return new ArrayList<SeasonCustList>(0);
		return seasonCustListDao.selectSeasonCustListByUrlPosIdList(url, posIdList);
	}

	public List<SeasonCustList> querySeasonCustDataByCoreWord(String custName, List<Long> posIdList, int limit) {
		List<SeasonCustList> resultList = new ArrayList<SeasonCustList>(0);
		if (CollectionUtils.isEmpty(posIdList))
			return resultList;
		if (Utils.isEqualLessThanZero(limit))
			return resultList;

		List<SaleData> saleDataList = tinyseMgr.querySeasonCustDataWithHold(custName, limit);
		for (SaleData saleData : saleDataList) {
			SeasonCustList seasonCustList = seasonCustListDao.findById(saleData.getId());
			for (Long posId : posIdList) {
				if (posId.equals(seasonCustList.getPosId())) {
					if (Utils.isEqualToZero(seasonCustList.getDelFlag().intValue()) && (Utils.isNull(seasonCustList.getInvalidate()) || new Date().before(seasonCustList.getInvalidate()))) {
						resultList.add(seasonCustList);
						break;
					}
				}
			}
		}
		return resultList;
	}

	public List<SeasonCustList> querySeasonCustDataByDomain(String domain, List<Long> posIdList) {
		if (CollectionUtils.isEmpty(posIdList))
			return new ArrayList<SeasonCustList>(0);
		if (StringUtils.isBlank(domain))
			return new ArrayList<SeasonCustList>(0);

		return seasonCustListDao.selectSeasonCustListByDomainPosIdList(domain, posIdList);
	}

	public List<SeasonCustList> querySeasonCustDataByPhone(String phone, List<Long> posIdList) {
		if (CollectionUtils.isEmpty(posIdList))
			return new ArrayList<SeasonCustList>(0);
		if (StringUtils.isBlank(phone))
			return new ArrayList<SeasonCustList>(0);

		return seasonCustListDao.selectSeasonCustListByPhoneNumPosIdList(phone, posIdList);
	}

	@Override
	public SqlMapDao<SeasonCustList, Long> getDao() {
		return seasonCustListDao;
	}

	public List<Long> selectDisCreateIdByPosIdList(List<Long> posIdList) {
		if (CollectionUtils.isEmpty(posIdList))
			return new ArrayList<Long>(0);
		return seasonCustListDao.selectDisCreateIdByPosIdList(posIdList);
	}

	public Long pageCount(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList) {
		if (CollectionUtils.isEmpty(posIdList))
			return NumberUtils.LONG_ZERO;

		if (StringUtils.isBlank(custName))
			custName = null;
		else
			custName = Utils.addWildcard(Utils.escapeWildcard(custName));

		if (StringUtils.isBlank(url))
			url = null;
		else
			url = Utils.addWildcard(Utils.escapeWildcard(url));

		if (Utils.isNull(createId) || Utils.isEqualToMinusOne(createId))
			createId = null;

		if (StringUtils.isBlank(phoneNum))
			phoneNum = null;

		if (Utils.isNotNullAndEqualToOne(useInvalidate))
			useInvalidate = null;

		return seasonCustListDao.pageCount(custName, url, createId, phoneNum, useInvalidate, beginInvalidate, endInvalidate, posIdList);
	}

	public List<SeasonCustList> pageList(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList, int curPage, int pageSize) {
		if (CollectionUtils.isEmpty(posIdList))
			return new ArrayList<SeasonCustList>(0);

		if (StringUtils.isBlank(custName))
			custName = null;
		else
			custName = Utils.addWildcard(Utils.escapeWildcard(custName));

		if (StringUtils.isBlank(url))
			url = null;
		else
			url = Utils.addWildcard(Utils.escapeWildcard(url));

		if (Utils.isNullOrEqualToMinusOne(createId))
			createId = null;

		if (StringUtils.isBlank(phoneNum))
			phoneNum = null;

		if (Utils.isNotNullAndEqualToOne(useInvalidate))
			useInvalidate = null;

		if (Utils.isLessThanZero(curPage))
			curPage = NumberUtils.INTEGER_ZERO;

		return seasonCustListDao.pageList(custName, url, createId, phoneNum, useInvalidate, beginInvalidate, endInvalidate, posIdList, curPage, pageSize);
	}

	public void deleteSeasonCustList(Long id) {
		if (Utils.isNotNull(id))
			seasonCustListDao.deleteSeasonCustList(id);
	}
}
