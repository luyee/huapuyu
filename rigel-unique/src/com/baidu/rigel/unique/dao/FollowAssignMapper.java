package com.baidu.rigel.unique.dao;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.FollowAssign;

@SqlMapper
public interface FollowAssignMapper extends DaoMapper<FollowAssign, Long> {
	public Long selectFollowIdByCustId(Long custId);
}