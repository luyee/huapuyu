package com.baidu.rigel.unique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.FollowAssign;
import com.baidu.rigel.unique.dao.FollowAssignDao;
import com.baidu.rigel.unique.service.FollowAssignService;
import com.baidu.rigel.unique.utils.Utils;

@Service("followAssignService")
public class FollowAssignServiceImpl extends GenericSqlMapServiceImpl<FollowAssign, Long> implements FollowAssignService {

	@Autowired
	private FollowAssignDao followAssignDao;

	public Long getFollowerId(Long custId) {
		if (Utils.isNull(custId))
			return null;

		return followAssignDao.selectFollowIdByCustId(custId);
	}

	@Override
	public SqlMapDao<FollowAssign, Long> getDao() {
		return followAssignDao;
	}
}
