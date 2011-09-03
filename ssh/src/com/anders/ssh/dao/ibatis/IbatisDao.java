package com.anders.ssh.dao.ibatis;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.anders.ssh.dao.Dao;
import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class IbatisDao<PK extends Serializable, T> extends SqlMapClientDaoSupport implements Dao<PK, T>
{
	// 增加setSqlMapClientMocker方法，避免在XML文件中给DAO方法注入SqlMapClient。
	@Resource
	public void setSqlMapClientMocker(SqlMapClient sqlMapClient)
	{
		super.setSqlMapClient(sqlMapClient);
	}
}
