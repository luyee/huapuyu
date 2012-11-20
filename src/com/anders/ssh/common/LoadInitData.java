/*
 * Copyright (c) 2009 Hewlett-Packard Company, All Rights Reserved.
 *
 * RESTRICTED RIGHTS LEGEND Use, duplication, or disclosure by the U.S.
 * Government is subject to restrictions as set forth in sub-paragraph
 * (c)(1)(ii) of the Rights in Technical Data and Computer Software
 * clause in DFARS 252.227-7013.
 *
 * Hewlett-Packard Company
 * 3000 Hanover Street
 * Palo Alto, CA 94304 U.S.A.
 * Rights for non-DOD U.S. Government Departments and Agencies are as
 * set forth in FAR 52.227-19(c)(1,2).
 */
package com.anders.ssh.common;

import javax.annotation.PostConstruct;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
// 原来没有加@DependsOn，造成先导入初始化数据，后hibernate创建表
@DependsOn("sessionFactory")
public class LoadInitData {
	private IDatabaseTester databaseTester = null;
	@Config(name = "driver")
	private String driver;
	@Config(name = "url")
	private String url;
	@Config(name = "username")
	private String userName;
	@Config(name = "password")
	private String password;
	@Config(name = "load.init.data.enable")
	private Boolean enable;
	@Config(name = "load.init.data.filename")
	private String fileName;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@PostConstruct
	public void load() throws Exception {
		databaseTester = new JdbcDatabaseTester(this.driver, this.url, this.userName, this.password);
		// databaseTester = new JdbcDatabaseTester(this.driver, this.url, this.userName, this.password, this.userName);

		if (this.enable) {
			IDataSet dataSet = new XmlDataSet(getClass().getResourceAsStream(fileName));
			databaseTester.setDataSet(dataSet);
		}
		else {
			IDataSet dataSet = new DefaultDataSet();
			databaseTester.setDataSet(dataSet);
		}
		databaseTester.onSetup();
	}
}
