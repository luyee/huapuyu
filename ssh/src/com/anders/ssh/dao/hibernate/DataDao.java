package com.anders.ssh.dao.hibernate;


import org.springframework.stereotype.Component;

import com.anders.ssh.model.Data;

@Component(value = "hibernateDataDao")
//@Component
public class DataDao extends BaseDao<Integer, Data>
{
}
