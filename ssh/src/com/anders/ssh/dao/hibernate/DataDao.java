package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.Data;

@Component("hibernateDataDao")
public class DataDao extends HibernateDao<Long, Data> {
}
