package com.anders.vote.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.Poll;
import com.anders.vote.dao.PollDao;
import com.anders.vote.mapper.PollMapper;

@Repository
public class PollDaoImpl implements PollDao {

	@Autowired
	private PollMapper pollMapper;

	@Override
	public Poll getById(Long id) {
		return pollMapper.getById(id);
	}

	@Override
	public void deleteById(Long id) {
		pollMapper.deleteById(id);

	}

	@Override
	public void save(Poll poll) {
		pollMapper.save(poll);
	}

	@Override
	public void update(Poll poll) {
		pollMapper.update(poll);
	}

	@Override
	public List<Poll> getAll() {
		return pollMapper.getAll();
	}

	@Override
	public void delete(Poll poll) {
		pollMapper.delete(poll);
	}

	//TODO Anders Zhu : 看下hibernate是如何实现的
	@Override
	public void saveOrUpdate(Poll poll) {
		if (pollMapper.findById(poll.getId()) == null) {
			pollMapper.save(poll);
		}
		else {
			pollMapper.update(poll);
		}
	}

	@Override
	public Poll findById(Long id) {
		return pollMapper.findById(id);
	}

	@Override
	public List<Poll> findAll() {
		return pollMapper.findAll();
	}

	@Override
	public void disabledById(Long id) {
		pollMapper.disabledById(id);
	}

	@Override
	public void disabled(Poll poll) {
		pollMapper.disabled(poll);
	}

	public PollMapper getPollMapper() {
		return pollMapper;
	}

	public void setPollMapper(PollMapper pollMapper) {
		this.pollMapper = pollMapper;
	}

	
}
