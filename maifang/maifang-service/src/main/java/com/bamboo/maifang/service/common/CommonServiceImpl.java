package com.bamboo.maifang.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bamboo.maifang.api.common.CommonService;
import com.bamboo.maifang.beans.Condition;
import com.bamboo.maifang.dao.AreaDao;
import com.bamboo.maifang.dao.DataDao;
import com.bamboo.maifang.dao.util.ConstantUtil;
import com.bamboo.maifang.model.Area;
import com.bamboo.maifang.model.Data;
import com.bamboo.maifang.model.Data.DataType;


@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	private AreaDao areaDao;

	@Autowired
	private DataDao dataDao;

	public List<Area> getSonAreasBy(Long parentId) {
		Condition condition = new Condition("parentArea.id", ConstantUtil.CompareType.EQ, parentId);
		condition.setAscOrders("id");
		return areaDao.getByCriteria(condition);
	}

	@Override
	public List<Data> getData(DataType dataType) {
		Condition condition = new Condition("type", ConstantUtil.CompareType.EQ, dataType);
		condition.setAscOrders("id");
		return dataDao.getByCriteria(condition);
	}

}
