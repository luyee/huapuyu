package com.anders.vote.dao;

import com.anders.vote.bo.Tester;

public interface TesterDao {
	void save(Tester tester);

	void update(Tester tester);

	void delete(Tester tester);
}
