package com.anders.ssh.dao.datajpa;

import org.springframework.data.repository.Repository;

import com.anders.ssh.bo.test.Account;

public interface AccountDao extends Repository<Account, Long> {
}
