package com.bamboo.maifang.api.common;

import java.util.List;

import com.bamboo.maifang.model.Area;

public interface CommonService {
	/**
	 * 
	 * @param parentId
	 * @return 子区域列表
	 */
	public List<Area> getSonAreasBy(Long parentId);
}
