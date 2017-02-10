package com.bamboo.maifang.action.rent;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bamboo.maifang.action.skeleton.MetadataUnitAction;
import com.bamboo.maifang.action.vo.RentHouseVO;
import com.bamboo.maifang.api.rent.RentService;
import com.bamboo.maifang.model.Data;
import com.bamboo.maifang.model.Data.DataType;
import com.bamboo.maifang.model.RentHouse;
import com.bamboo.maifang.model.User;

@Component("rentHouseAction")
@Scope("singleton")
public class RentHouseAction extends MetadataUnitAction {

	private static final long serialVersionUID = 1L;

	private RentHouseVO rentHouse;
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
		RentHouse house = new RentHouse();
		try {
			PropertyUtils.copyProperties(house, rentHouse);
			rentService.createRentHouse(house);
			this.createFlag = true;
			return SUCCESS;
		}
		catch (Exception e) {
			return ERROR;
		}
	}

	public RentHouseVO getRentHouse() {
		return rentHouse;
	}

	public void setRentHouse(RentHouseVO rentHouse) {
		this.rentHouse = rentHouse;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
