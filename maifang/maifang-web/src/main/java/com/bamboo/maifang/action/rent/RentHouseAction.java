package com.bamboo.maifang.action.rent;

import org.springframework.beans.factory.annotation.Autowired;

import com.bamboo.maifang.action.skeleton.MetadataUnitAction;
import com.bamboo.maifang.api.rent.RentService;
import com.bamboo.maifang.model.Data;
import com.bamboo.maifang.model.Data.DataType;
import com.bamboo.maifang.model.RentHouse;
import com.bamboo.maifang.model.User;

public class RentHouseAction extends MetadataUnitAction {

	private static final long serialVersionUID = 1L;

	private RentHouse rentHouse;
	private User user;

	@Autowired
	private RentService rentService;

	public RentHouseAction() {
	}

	public String onLoad() {
		this.doGetAreas(2L);
		this.doGetPayMentData(DataType.PAYMENT);
		this.doGetDealTimeData(DataType.DEAL_TIME);
		this.doGetVisitTimeData(DataType.VISIT_TIME);
		this.doGetRentPeriodData(DataType.RENT_PERIOD);
		this.doGetDecorationData(DataType.DECORATION);
		this.doGetResidenceTypeData(DataType.RESIDENCE_TYPE);
		this.doGetOrientationData(DataType.ORIENTATION);
		this.doGetConstructionYearData(DataType.CONSTRUCTION_YEAR);

		return SUCCESS;
	}

	public String submitCreate() {
		// TODO
		this.rentHouse.setRentType(new Data(1101L));

		rentService.createRentHouse(this.rentHouse);
		this.createFlag = true;
		return SUCCESS;
	}

	public RentHouse getRentHouse() {
		return rentHouse;
	}

	public void setRentHouse(RentHouse rentHouse) {
		this.rentHouse = rentHouse;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
