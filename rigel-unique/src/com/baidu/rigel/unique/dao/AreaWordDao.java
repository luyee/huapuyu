package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.AreaWord;

public interface AreaWordDao extends SqlMapDao<AreaWord, Long> {
	public List<AreaWord> selectAllOrderByCWord();
}