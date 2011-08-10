package com.baidu.rigel.unique.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.ShifenCustWhiteList;
import com.baidu.rigel.unique.dao.ShifenCustWhiteListDao;
import com.baidu.rigel.unique.service.ShifenCustWhiteListService;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.Utils;

@Service("shifenCustWhiteListService")
public class ShifenCustWhiteListServiceImpl extends GenericSqlMapServiceImpl<ShifenCustWhiteList, Long> implements ShifenCustWhiteListService {

	@Autowired
	private ShifenCustWhiteListDao shifenCustWhiteListDao;

	public ShifenCustWhiteList equalUrl(String url) {
		if (StringUtils.isBlank(url))
			return null;
		return shifenCustWhiteListDao.selectShifenCustWhiteListByUrl(url);
	}

	@Override
	public SqlMapDao<ShifenCustWhiteList, Long> getDao() {
		return shifenCustWhiteListDao;
	}

	public List<Long> selectDisUserIdByPosIdList(List<Long> posIdList) {
		return shifenCustWhiteListDao.selectDisUserIdByPosIdList(posIdList);
	}

	public List<Long> selectDisCreateIdByPosIdList(List<Long> posIdList) {
		return shifenCustWhiteListDao.selectDisCreateIdByPosIdList(posIdList);
	}

	public Long pageCount(String custName, String url, Long userId, Long createId, List<Long> posIdList) {
		if (StringUtils.isBlank(custName))
			custName = null;
		else
			custName = Utils.addWildcard(Utils.escapeWildcard(custName));

		if (StringUtils.isBlank(url))
			url = null;
		else
			url = Utils.addWildcard(Utils.escapeWildcard(url));

		return shifenCustWhiteListDao.pageCount(custName, url, userId, createId, posIdList);
	}

	public List<ShifenCustWhiteList> pageList(String custName, String url, Long userId, Long createId, List<Long> posIdList, int curPage, int pageSize) {
		if (StringUtils.isBlank(custName))
			custName = null;
		else
			custName = Utils.addWildcard(Utils.escapeWildcard(custName));

		if (StringUtils.isBlank(url))
			url = null;
		else
			url = Utils.addWildcard(Utils.escapeWildcard(url));

		if (Utils.isLessThanZero(curPage))
			curPage = Constant.ZERO;

		return shifenCustWhiteListDao.pageList(custName, url, userId, createId, posIdList, curPage, pageSize);
	}

	public void deleteShifenCustWhiteList(Long id) {
		if (Utils.isNotNull(id))
			shifenCustWhiteListDao.deleteShifenCustWhiteList(id);
	}

}
