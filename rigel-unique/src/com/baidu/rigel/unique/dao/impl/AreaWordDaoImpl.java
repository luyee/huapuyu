package com.baidu.rigel.unique.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.AreaWord;
import com.baidu.rigel.unique.dao.AreaWordDao;
import com.baidu.rigel.unique.dao.AreaWordMapper;

@Service("areaWordDao")
public class AreaWordDaoImpl extends BaseSqlMapDao<AreaWord, Long> implements AreaWordDao {
	@Autowired
	private AreaWordMapper areaWordMapper;

	@Override
	public DaoMapper<AreaWord, Long> getDaoMapper() {
		return areaWordMapper;
	}

	public List<AreaWord> selectAllOrderByCWord() {
		return areaWordMapper.selectAllOrderByCWord();
	}
}