package com.bamboo.maifang.service.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bamboo.maifang.api.rent.RentService;
import com.bamboo.maifang.dao.AreaDao;
import com.bamboo.maifang.model.RentHouse;

@Service("rentService")
public class RentServiceImpl implements RentService {
	
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public void createRentHouse(RentHouse house) {
		areaDao.saveNotUpdate(house);
	}

}
