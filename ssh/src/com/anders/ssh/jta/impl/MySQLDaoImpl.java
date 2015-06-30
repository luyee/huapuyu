package com.anders.ssh.jta.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.jta.MySQLDao;

public class MySQLDaoImpl extends JdbcDaoSupport implements MySQLDao {

	@Override
	public void save(Data data) {
		String sql = "insert into cfg_data (id, name, type, enable) values(?, ?, ?, ?)";
		Object[] params = new Object[] { data.getId(), data.getName(), data.getType(), data.getEnable() };
		this.getJdbcTemplate().update(sql, params);

	}

}
