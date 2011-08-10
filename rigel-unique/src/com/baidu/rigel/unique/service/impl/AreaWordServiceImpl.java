package com.baidu.rigel.unique.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.platform.service.impl.GenericSqlMapServiceImpl;
import com.baidu.rigel.unique.bo.AreaWord;
import com.baidu.rigel.unique.dao.AreaWordDao;
import com.baidu.rigel.unique.service.AreaWordService;
import com.baidu.rigel.unique.utils.Utils;

@Service("areaWordService")
public class AreaWordServiceImpl extends GenericSqlMapServiceImpl<AreaWord, Long> implements AreaWordService {
	// TODO Anders Zhu :重构前的类有缓存管理，请注意
	@Autowired
	private AreaWordDao areaWordDao;

	@Override
	public SqlMapDao<AreaWord, Long> getDao() {
		return areaWordDao;
	}

	public List<AreaWord> selectAllOrderByCWord() {
		return areaWordDao.selectAllOrderByCWord();
	}

	public void deleteAreaWord(Long id) {
		if (Utils.isNotNull(id))
			areaWordDao.delete(id);
	}

}
