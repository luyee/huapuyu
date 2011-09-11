package com.bamboo.maifang.action.rent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bamboo.maifang.action.BaseAction;
import com.bamboo.maifang.api.common.CommonService;
import com.bamboo.maifang.model.Area;

public class RentHouseAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private List<Area> areas;
	
	private CommonService commonService;
	
	public RentHouseAction() {
	}
	
	
	public String onLoad() {
		this.areas = this.commonService.getSonAreasBy(21L);
		return SUCCESS;
	}


	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}


	public List<Area> getAreas() {
		return areas;
	}


	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
}
