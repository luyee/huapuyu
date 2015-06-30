package com.bamboo.maifang.api.common;

import java.util.List;

import com.bamboo.maifang.model.Area;
import com.bamboo.maifang.model.Data;

public interface CommonService {
	/**
	 * 
	 * @param parentId
	 * @return 子区域列表
	 */
	public List<Area> getSonAreasBy(Long parentId);
	
	/**
	 * 
	 * @param parentId
	 * @return 得到所属类型的Data
	 */
	public List<Data> getData(Data.DataType dataType);
}
