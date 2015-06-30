package com.anders.ssh.jta.impl;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.jta.JtaFacade1;
import com.anders.ssh.jta.MySQLDao;
import com.anders.ssh.jta.OracleDao;

public class JtaFacade1Impl implements JtaFacade1 {
	private MySQLDao mySQLDao;

	private OracleDao oracleDao;

	@Override
	public void save(Data data) {
		oracleDao.save(data);
		mySQLDao.save(data);
	}

	public MySQLDao getMySQLDao() {
		return mySQLDao;
	}

	public void setMySQLDao(MySQLDao mySQLDao) {
		this.mySQLDao = mySQLDao;
	}

	public OracleDao getOracleDao() {
		return oracleDao;
	}

	public void setOracleDao(OracleDao oracleDao) {
		this.oracleDao = oracleDao;
	}
}
