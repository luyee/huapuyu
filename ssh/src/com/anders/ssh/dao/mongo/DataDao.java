package com.anders.ssh.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.Data;

@Component("mongoDataDao")
public class DataDao extends MongoDao<Integer, Data> {

	@Override
	public void delete(Data data) {
		mongoTemplate.remove(data);
	}

	@Override
	public void deleteById(Integer id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Data.class);
	}

	@Override
	public List<Data> getAll() {
		return mongoTemplate.findAll(Data.class);
	}

	@Override
	public Data getById(Integer id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Data.class);
	}

	@Override
	public void save(Data data) {
		// mongoTemplate.save(data);
		mongoTemplate.insert(data);
	}

	@Override
	public void update(Data data) {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is(data.getId())), Update.update("name", data.getName()), Data.class);
	}
}
