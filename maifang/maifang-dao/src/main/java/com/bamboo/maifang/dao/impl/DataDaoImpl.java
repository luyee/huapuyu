package com.bamboo.maifang.dao.impl;

import org.springframework.stereotype.Repository;

import com.bamboo.maifang.dao.BaseDaoSkeleton;
import com.bamboo.maifang.dao.DataDao;
import com.bamboo.maifang.model.Data;

@Repository("dataDao")
public class DataDaoImpl extends BaseDaoSkeleton<Data, Long> implements DataDao {

}
