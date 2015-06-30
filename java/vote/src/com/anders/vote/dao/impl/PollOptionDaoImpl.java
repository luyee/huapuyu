package com.anders.vote.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.PollOption;
import com.anders.vote.dao.PollOptionDao;
import com.anders.vote.mapper.GenericMapper;
import com.anders.vote.mapper.PollOptionMapper;

@Repository
public class PollOptionDaoImpl extends GenericDaoImpl<Long, PollOption> implements PollOptionDao {

	@Autowired
	private PollOptionMapper pollOptionMapper;

	@Override
	public GenericMapper<Long, PollOption> getMapper() {
		return pollOptionMapper;
	}

}
