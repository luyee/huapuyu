package com.anders.ssh.dao.mongo;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.anders.ssh.dao.Dao;

public abstract class MongoDao<PK extends Serializable, T> implements Dao<PK, T> {
	protected MongoTemplate mongoTemplate;

	@Resource
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
}