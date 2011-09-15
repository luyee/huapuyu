package com.bamboo.maifang.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bamboo.maifang.api.common.CommonService;
import com.bamboo.maifang.beans.Condition;
import com.bamboo.maifang.dao.AreaDao;
import com.bamboo.maifang.dao.util.ConstantUtil;
import com.bamboo.maifang.model.Area;


@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	private AreaDao areaDao;

	public List<Area> getSonAreasBy(Long parentId) {
		Condition condition = new Condition("parentArea.id", ConstantUtil.CompareType.EQ, parentId);
		condition.setAscOrders("id");
		return areaDao.getByCriteria(condition);
	}
	
	
}
