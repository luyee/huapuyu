/*
 * Copyright (c) 2013, OpenCloudDB/MyCAT and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software;Designed and Developed mainly by many Chinese
 * opensource volunteers. you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 2 only, as published by the
 * Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Any questions about this component can be directed to it's project Web address
 * https://code.google.com/p/opencloudb/.
 *
 */
package io.mycat.server.config;

import io.mycat.backend.PhysicalDBPool;
import io.mycat.server.SystemConfig;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Datahost is a group of DB servers which is synchronized with each other
 *
 * @author wuzhih
 *
 */
public class DataHostConfig {
	public static final int NOT_SWITCH_DS = -1;
	public static final int DEFAULT_SWITCH_DS = 1;
	public static final int SYN_STATUS_SWITCH_DS = 2;
    private static final Pattern pattern = Pattern.compile("\\s*show\\s+slave\\s+status\\s*",Pattern.CASE_INSENSITIVE);
	private String name;
	private int maxCon = SystemConfig.DEFAULT_POOL_SIZE;
	private int minCon = 10;
	private int balance = PhysicalDBPool.BALANCE_NONE;
	private int writeType = PhysicalDBPool.WRITE_ONLYONE_NODE;
	private final String dbType;
	private final String dbDriver;
	private final DBHostConfig[] writeHosts;
	private final Map<Integer, DBHostConfig[]> readHosts;
	private String hearbeatSQL;
    private boolean isShowSlaveSql=false;
	private String connectionInitSql;
    private int slaveThreshold = -1;
	private final int switchType;
	private String filters="mergeStat";
	private long logTime=300000;

	public DataHostConfig(String name, String dbType, String dbDriver,
			DBHostConfig[] writeHosts, Map<Integer, DBHostConfig[]> readHosts,int switchType,int slaveThreshold) {
		super();
		this.name = name;
		this.dbType = dbType;
		this.dbDriver = dbDriver;
		this.writeHosts = writeHosts;
		this.readHosts = readHosts;
		this.switchType=switchType;
		this.slaveThreshold=slaveThreshold;
	}

	public int getSlaveThreshold() {
		return slaveThreshold;
	}

	public void setSlaveThreshold(int slaveThreshold) {
		this.slaveThreshold = slaveThreshold;
	}

	public int getSwitchType() {
		return switchType;
	}

	public String getConnectionInitSql()
	{
		return connectionInitSql;
	}

	public void setConnectionInitSql(String connectionInitSql)
	{
		this.connectionInitSql = connectionInitSql;
	}

	public int getWriteType() {
		return writeType;
	}

	public void setWriteType(int writeType) {
		this.writeType = writeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxCon() {
		return maxCon;
	}

	public void setMaxCon(int maxCon) {
		this.maxCon = maxCon;
	}

	public int getMinCon() {
		return minCon;
	}

	public void setMinCon(int minCon) {
		this.minCon = minCon;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getDbType() {
		return dbType;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public DBHostConfig[] getWriteHosts() {
		return writeHosts;
	}

	public Map<Integer, DBHostConfig[]> getReadHosts() {
		return readHosts;
	}

	public String getHearbeatSQL() {
		return hearbeatSQL;
	}

	public void setHearbeatSQL(String heartbeatSQL) {
		this.hearbeatSQL = heartbeatSQL;
        Matcher matcher = pattern.matcher(heartbeatSQL);
        if (matcher.find())
        {
            isShowSlaveSql=true;
        }
	}
    public boolean isShowSlaveSql()
    {
        return isShowSlaveSql;
    }
	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public long getLogTime() {
		return logTime;
	}

	public void setLogTime(long logTime) {
		this.logTime = logTime;
	}


}