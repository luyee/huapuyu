package com.baidu.rigel.unique.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.FollowAssign;
import com.baidu.rigel.unique.dao.FollowAssignDao;
import com.baidu.rigel.unique.dao.FollowAssignMapper;

@Service("followAssignDao")
public class FollowAssignDaoImpl extends BaseSqlMapDao<FollowAssign, Long> implements FollowAssignDao {
	@Autowired
	private FollowAssignMapper followAssignMapper;

	@Override
	public DaoMapper<FollowAssign, Long> getDaoMapper() {
		return followAssignMapper;
	}

	public Long selectFollowIdByCustId(Long custId) {
		return followAssignMapper.selectFollowIdByCustId(custId);
	}

}