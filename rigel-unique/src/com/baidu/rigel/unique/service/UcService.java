package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.service.usercenter.bean.Position;

public interface UcService {
	public Map<Long, Position> getPositionMapByUcidListAndRootTag(List<Long> ucidList, String rootTag);
}
