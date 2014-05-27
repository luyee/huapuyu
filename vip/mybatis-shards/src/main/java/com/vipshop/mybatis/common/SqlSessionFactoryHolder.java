package com.vipshop.mybatis.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.TransactionStatus;

/**
 * 事务信息、数据源、事务状态Holder
 * 
 * @author Anders
 * 
 */
public class SqlSessionFactoryHolder {
//	private static ThreadLocal<Map<DataSource, SqlSessionFactory>> DS2SSF = new ThreadLocal<Map<DataSource, SqlSessionFactory>>();
//
//	public static void addDataSource2SqlSessionFactory(DataSource dataSource, SqlSessionFactory sqlSessionFactory) {
//		Map<DataSource, SqlSessionFactory> map = DS2SSF.get();
//		if (map == null) {
//			map = new HashMap<DataSource, SqlSessionFactory>();
//			DS2SSF.set(map);
//		}
//
//		map.put(dataSource, sqlSessionFactory);
//	}
//
//	public static Map<DataSource, SqlSessionFactory> getDataSource2SqlSessionFactory() {
//		return DS2SSF.get();
//	}
	
	private static ThreadLocal<Map<Long, SqlSessionFactory>> DS2SSF = new ThreadLocal<Map<Long, SqlSessionFactory>>();

	public static void addDataSource2SqlSessionFactory(Long dataSource, SqlSessionFactory sqlSessionFactory) {
		Map<Long, SqlSessionFactory> map = DS2SSF.get();
		if (map == null) {
			map = new HashMap<Long, SqlSessionFactory>();
			DS2SSF.set(map);
		}

		map.put(dataSource, sqlSessionFactory);
	}

	public static SqlSessionFactory getDataSource2SqlSessionFactory(long i) {
		return DS2SSF.get().get(i);
	}
}
