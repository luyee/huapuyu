package com.anders.vote.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.Poll;
import com.anders.vote.dao.PollDao;
import com.anders.vote.mapper.GenericMapper;
import com.anders.vote.mapper.PollMapper;

@Repository
public class PollDaoImpl extends GenericDaoImpl<Long, Poll> implements PollDao {

	@Autowired
	private PollMapper pollMapper;

	public PollMapper getPollMapper() {
		return pollMapper;
	}

	public void setPollMapper(PollMapper pollMapper) {
		this.pollMapper = pollMapper;
	}

	@Override
	public GenericMapper<Long, Poll> getMapper() {
		return pollMapper;
	}

}
