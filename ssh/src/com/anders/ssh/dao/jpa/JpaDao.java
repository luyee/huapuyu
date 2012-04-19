package com.anders.ssh.dao.jpa;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.anders.ssh.dao.Dao;

public abstract class JpaDao<PK extends Serializable, T> extends JpaDaoSupport implements Dao<PK, T> {
	// 增加setEntityManagerFactoryMocker方法，避免在XML文件中给DAO方法注入EntityManagerFactory。
	@Resource
	public void setSuperEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		super.setEntityManagerFactory(entityManagerFactory);
	}
}
