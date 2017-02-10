package com.anders.jdbc.test.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.anders.jdbc.test.entity.Cust;

public interface CustService {
	void insert(Cust cust)throws IOException;

	void insertS(Cust cust) throws SQLException;

	void deleteById(Long id);

	void update(Cust cust);

	Cust selectById(Long id);

	Cust selectByIdS(Long id) throws SQLException;

	List<Cust> selectByIds(List<Long> ids);

	List<Cust> selectBy(Cust cust);

	Cust selectByConstant();

	Cust selectByJoin();

	int selectCount(List<Long> ids);

	int selectMax(List<Long> ids);

	int selectMin(List<Long> ids);

	BigDecimal selectAvg(List<Long> ids);

	int selectSum(List<Long> ids);

	Map<String, Object> selectFuns(List<Long> ids);
}
