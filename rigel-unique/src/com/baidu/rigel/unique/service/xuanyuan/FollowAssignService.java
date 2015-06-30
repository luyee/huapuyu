package com.baidu.rigel.unique.service.xuanyuan;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.xuanyuan.FollowAssign;

public interface FollowAssignService extends GenericService<FollowAssign, Long> {
	public Long selectFollowIdByCustId(Long custId);
}
