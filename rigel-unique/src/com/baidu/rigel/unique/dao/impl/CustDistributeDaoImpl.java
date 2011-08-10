package com.baidu.rigel.unique.dao.impl;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.CustDistribute;
import com.baidu.rigel.unique.dao.CustDistributeDao;
import com.baidu.rigel.unique.dao.CustDistributeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("custDistributeDao")
public class CustDistributeDaoImpl extends BaseSqlMapDao<CustDistribute, Long> implements CustDistributeDao {
    @Autowired
    private CustDistributeMapper custDistributeMapper;

    @Override
    public DaoMapper<CustDistribute, Long> getDaoMapper() {
        return custDistributeMapper;
    }
}