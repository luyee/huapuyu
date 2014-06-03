package com.vipshop.mybatis.common;

import com.vipshop.mybatis.SqlSessionFactoryBean;

public class SqlSessionFactoryHolder {
	
	private static ThreadLocal<SqlSessionFactoryBean> SSFB_HOLDER = new ThreadLocal<SqlSessionFactoryBean>();

	public static SqlSessionFactoryBean getSqlSessionFactoryBean() {
		return SSFB_HOLDER.get();
	}

	public static void setSqlSessionFactoryBean(
			SqlSessionFactoryBean sqlSessionFactoryBean) {
		SSFB_HOLDER.set(sqlSessionFactoryBean);
	}

}
