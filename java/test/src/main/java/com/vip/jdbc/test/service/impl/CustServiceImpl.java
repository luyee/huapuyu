package com.vip.jdbc.test.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vip.jdbc.test.entity.Cust;
import com.vip.jdbc.test.repository.CustMapper;
import com.vip.jdbc.test.service.CustService;

@Transactional(rollbackFor = Throwable.class)
@Service("custService")
public class CustServiceImpl implements CustService {

	@Resource
	private DataSource dataSource;

	@Resource(name = "custMapper")
	private CustMapper custMapper;

	@Transactional
	public void insert(Cust cust) throws IOException {
		custMapper.insert(cust);
	}

	@Transactional
	public void deleteById(Long id) {
		custMapper.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Cust selectById(Long id) {
		return custMapper.selectById(id);
	}

	@Transactional
	public void update(Cust cust) {
		custMapper.update(cust);
	}

	@Transactional(readOnly = true)
	public List<Cust> selectByIds(List<Long> ids) {
		return custMapper.selectByIds(ids);
	}

	public List<Cust> selectBy(Cust cust) {
		return custMapper.selectBy(cust);
	}

	public Cust selectByConstant() {
		return custMapper.selectByConstant();
	}

	public Cust selectByJoin() {
		return custMapper.selectByJoin();
	}

	public int selectCount(List<Long> ids) {
		return custMapper.selectCount(ids);
	}

	public int selectMax(List<Long> ids) {
		return custMapper.selectMax(ids);
	}

	public int selectMin(List<Long> ids) {
		return custMapper.selectMin(ids);
	}

	public BigDecimal selectAvg(List<Long> ids) {
		return custMapper.selectAvg(ids);
	}

	public int selectSum(List<Long> ids) {
		return custMapper.selectSum(ids);
	}

	public Map<String, Object> selectFuns(List<Long> ids) {
		return custMapper.selectFuns(ids);
	}

	@Override
	public void insertS(Cust cust) throws SQLException {
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		statement.execute("insert into cust (id, name) values (" + cust.getId() + ", '" + cust.getName() + "')");
		connection.commit();
	}

	@Override
	public Cust selectByIdS(Long id) throws SQLException {
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from cust where id = " +id);
		Cust cust = new Cust();
		if (resultSet.next()) {
			cust.setId(resultSet.getLong(1));
			cust.setName(resultSet.getString(2));
		}
		return cust;
	}
}
