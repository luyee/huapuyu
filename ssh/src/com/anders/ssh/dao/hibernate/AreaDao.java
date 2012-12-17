package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.xml.Area;

@Component("hibernateAreaDao")
public class AreaDao extends HibernateDao<Long, Area> {
}
