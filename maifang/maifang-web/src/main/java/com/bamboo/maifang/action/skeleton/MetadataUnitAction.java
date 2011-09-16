package com.bamboo.maifang.action.skeleton;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bamboo.maifang.action.BaseAction;
import com.bamboo.maifang.api.common.CommonService;
import com.bamboo.maifang.model.Area;
import com.bamboo.maifang.model.Data;
import com.bamboo.maifang.model.Data.DataType;

public abstract class MetadataUnitAction extends BaseAction {

	private List<Area> areas;
	private List<Data> payMentData;
	private List<Byte> bedroomCounts;
	private List<Byte> livingRoomCounts;
	private List<Byte> washroomCounts;

	private CommonService commonService;

	protected void doGetAreas(Long parentCityId) {
		this.areas = this.commonService.getSonAreasBy(parentCityId);
	}

	protected void doGetPayMentData(DataType dataType) {
		this.payMentData = this.commonService.getData(dataType);
	}

	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public List<Byte> getBedroomCounts() {
		Byte[] roomCounts = new Byte[] { 1, 2, 3, 4, 5, 6, 7 };
		bedroomCounts = Arrays.asList(roomCounts);
		return bedroomCounts;
	}

	public List<Byte> getLivingRoomCounts() {
		Byte[] roomCounts = new Byte[] { 0, 1, 2, 3, 4 };
		livingRoomCounts = Arrays.asList(roomCounts);
		return livingRoomCounts;
	}

	public List<Byte> getWashroomCounts() {
		Byte[] roomCounts = new Byte[] { 0, 1, 2, 3, 4 };
		washroomCounts = Arrays.asList(roomCounts);
		return washroomCounts;
	}

	public List<Data> getPayMentData() {
		return payMentData;
	}

	public void setPayMentData(List<Data> payMentData) {
		this.payMentData = payMentData;
	}
}
