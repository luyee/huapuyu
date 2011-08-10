package com.baidu.rigel.unique.dao.impl;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.CustContact;
import com.baidu.rigel.unique.dao.CustContactDao;
import com.baidu.rigel.unique.dao.CustContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("custContactDao")
public class CustContactDaoImpl extends BaseSqlMapDao<CustContact, Long> implements CustContactDao {
    @Autowired
    private CustContactMapper custContactMapper;

    @Override
    public DaoMapper<CustContact, Long> getDaoMapper() {
        return custContactMapper;
    }
}