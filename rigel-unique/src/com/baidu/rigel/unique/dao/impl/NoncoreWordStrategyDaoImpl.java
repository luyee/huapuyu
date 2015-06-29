package com.baidu.rigel.unique.dao.impl;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.NoncoreWordStrategy;
import com.baidu.rigel.unique.dao.NoncoreWordStrategyDao;
import com.baidu.rigel.unique.dao.NoncoreWordStrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noncoreWordStrategyDao")
public class NoncoreWordStrategyDaoImpl extends BaseSqlMapDao<NoncoreWordStrategy, Long> implements NoncoreWordStrategyDao {
    @Autowired
    private NoncoreWordStrategyMapper noncoreWordStrategyMapper;

    @Override
    public DaoMapper<NoncoreWordStrategy, Long> getDaoMapper() {
        return noncoreWordStrategyMapper;
    }
}