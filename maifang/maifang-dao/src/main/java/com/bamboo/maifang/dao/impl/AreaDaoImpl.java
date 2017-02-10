package com.bamboo.maifang.dao.impl;

import org.springframework.stereotype.Repository;

import com.bamboo.maifang.dao.AreaDao;
import com.bamboo.maifang.dao.BaseDaoSkeleton;
import com.bamboo.maifang.model.Area;

@Repository("areaDao")
public class AreaDaoImpl extends BaseDaoSkeleton<Area, Long> implements AreaDao {
	
}
