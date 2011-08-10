package com.baidu.rigel.unique.service;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.FollowAssign;

public interface FollowAssignService extends GenericService<FollowAssign, Long> {
	public Long getFollowerId(Long custId);
}
