package com.anders.vote.mybatis;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.vote.mybatis.mapper.Mapper;
import com.anders.vote.mybatis.mapper.MySqlMapper;

public abstract class MapResolver {

	private final static Logger logger = LoggerFactory.getLogger(MapResolver.class);

	public abstract Object getCurrentEntityVersionInDatabase(Connection connection, EntityWrapper entityWrapper) throws SQLException;

	public static Mapper getMapper(Connection connection) {
		try {
			Class<? extends Mapper> mapperClass = null;
			String dbName = connection.getMetaData().getDatabaseProductName();
			Integer dbMajorVersion = connection.getMetaData().getDatabaseMajorVersion();
			Integer dbMinorVersion = connection.getMetaData().getDatabaseMinorVersion();
			if ("MySQL".equals(dbName)) {
				mapperClass = MySqlMapper.class;
			}
			if (mapperClass == null) {
				throw new RuntimeException("Could not find appropriate mapper for database " + dbName + ", major version " + dbMajorVersion + ", minor version " + dbMinorVersion);
			}
			return getMapper(mapperClass);
		}
		catch (SQLException e) {
			logger.error("Could not retrive database metadata.", e);
		}
		return null;
	}

	public static Mapper getMapper(Class<? extends Mapper> mapperClass) {
		try {
			return (Mapper) mapperClass.newInstance();
		}
		catch (Exception e) {
			logger.error("Could not instantiate mapper class.", e);
		}
		return null;
	}

	public static Mapper getMapper(String className) {
		try {
			@SuppressWarnings("unchecked")
			Class<Mapper> mapperClass = (Class<Mapper>) Class.forName(className);
			return getMapper(mapperClass);
		}
		catch (ClassNotFoundException e) {
			logger.error("Could not load mapper class.", e);
		}
		return null;
	}

}
