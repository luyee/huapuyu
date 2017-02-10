package com.anders.vote.mybatis.mapper;

import java.sql.Connection;
import java.sql.SQLException;

import com.anders.vote.mybatis.EntityWrapper;

public interface Mapper {

	boolean isCompatible(Connection connection);

	Object getCurrentEntityVersionInDatabase(Connection connection, EntityWrapper entityWrapper) throws SQLException;
}