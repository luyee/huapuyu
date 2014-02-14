package com.anders.crm.service;

import com.anders.crm.bo.LuUser;

public interface LuUserService extends GenericService<Long, LuUser> {
	void addLucene();

	void getLucene();
}
