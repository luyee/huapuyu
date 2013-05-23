package com.anders.ssh.jta.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.jta.OracleDao;

public class OracleDaoImpl extends JdbcDaoSupport implements OracleDao {

	@Override
	public void save(Data data) {
		String sql = "insert into cfg_data (id, name, type, enable) values(?, ?, ?, ?)";
		Object[] params = new Object[] { data.getId(), data.getName(), data.getType(), data.getEnable() };
		this.getJdbcTemplate().update(sql, params);
	}

}
