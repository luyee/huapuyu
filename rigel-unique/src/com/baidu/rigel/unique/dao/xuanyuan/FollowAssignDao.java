package com.baidu.rigel.unique.dao.xuanyuan;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.xuanyuan.FollowAssign;

public interface FollowAssignDao extends SqlMapDao<FollowAssign, Long> {
	public Long selectFollowIdByCustId(Long custId);
}