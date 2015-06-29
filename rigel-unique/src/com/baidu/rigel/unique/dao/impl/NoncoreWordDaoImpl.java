package com.baidu.rigel.unique.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.NoncoreWord;
import com.baidu.rigel.unique.dao.NoncoreWordDao;
import com.baidu.rigel.unique.dao.NoncoreWordMapper;
import com.baidu.rigel.unique.utils.ParamConstant;

@Service("noncoreWordDao")
public class NoncoreWordDaoImpl extends BaseSqlMapDao<NoncoreWord, Long> implements NoncoreWordDao {
	@Autowired
	private NoncoreWordMapper noncoreWordMapper;

	@Override
	public DaoMapper<NoncoreWord, Long> getDaoMapper() {
		return noncoreWordMapper;
	}

	public List<Long> selectDisCreatorId() {
		return noncoreWordMapper.selectDisCreatorId();
	}

	public Long pageCount(Long creatorId, String word) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CREATOR_ID, creatorId);
		paramMap.put(ParamConstant.WORD, word);
		return noncoreWordMapper.pageCount(paramMap);
	}

	public List<NoncoreWord> pageList(Long creatorId, String word, int curPage, int pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ParamConstant.CREATOR_ID, creatorId);
		paramMap.put(ParamConstant.WORD, word);
		paramMap.put(ParamConstant.START, curPage * pageSize);
		paramMap.put(ParamConstant.COUNT, pageSize);
		return noncoreWordMapper.pageList(paramMap);
	}

	public NoncoreWord selectNoncoreWordByWord(String word) {
		return noncoreWordMapper.selectNoncoreWordByWord(word);
	}
}