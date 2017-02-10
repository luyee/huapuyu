package com.anders.hibernate;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

public class Generate {
	@Test
	public void generate() {
		// TODO Anders Zhu :
		// 注意lib包中的jaybird21.dll和libjaybird21.so，如果生成Firebird数据库需要将这两个文件加入java.library.path（比如JAVA_HOME/bin下），否则会报“no
		// jaybird21 in java.library.path”
		System.out.println(System.getProperty("java.library.path"));
		System.out.println(SystemUtils.JAVA_LIBRARY_PATH);
		HibernateUtil.getSession();
		// Session session = HibernateUtil.getSession();
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
