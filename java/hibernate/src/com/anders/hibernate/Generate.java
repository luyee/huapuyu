package com.anders.hibernate;

import org.hibernate.Session;
import org.junit.Test;

public class Generate {
	@Test
	public void generate() {
		Session session = HibernateUtil.getSession();
		// Transaction tx = session.beginTransaction();
		// Tb_user user = new Tb_user();
		// user.setName("zhuzhen");
		// user.setPwd("123");
		// session.save(user);
		//
		// tx.commit();

		// House house = (House) session.get(House.class, 1L);
		//
		// StringBuilder sBuilder = new StringBuilder();
		// sBuilder.append(house.getName());
		// sBuilder.append(house.getCity().getName());
		// sBuilder.append(house.getConstructYear().getName());
		// sBuilder.append(house.getDistrict().getName());
		// sBuilder.append(house.getRentHouse().getRoommateGender().getName());
		// sBuilder.append(house.getSecondHandHouse().getPropType().getName());
		// System.out.println(sBuilder.toString());

		// System.out.println(user.getId());
	}

}
