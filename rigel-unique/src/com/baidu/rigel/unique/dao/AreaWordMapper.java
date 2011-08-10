package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.AreaWord;

@SqlMapper
public interface AreaWordMapper extends DaoMapper<AreaWord, Long> {
	public List<AreaWord> selectAllOrderByCWord();
}