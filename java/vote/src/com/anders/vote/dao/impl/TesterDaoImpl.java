package com.anders.vote.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.Tester;
import com.anders.vote.dao.TesterDao;
import com.anders.vote.mapper.TesterMapper;

@Repository
public class TesterDaoImpl implements TesterDao {

	@Autowired
	private TesterMapper testerMapper;

	@Override
	public void save(Tester tester) {
		testerMapper.save(tester);
	}

	@Override
	public void update(Tester tester) {
		testerMapper.update(tester);
	}

	@Override
	public void delete(Tester tester) {
		testerMapper.delete(tester);
	}
}
