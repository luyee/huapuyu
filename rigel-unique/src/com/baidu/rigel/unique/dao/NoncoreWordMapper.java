package com.baidu.rigel.unique.dao;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.NoncoreWord;

@SqlMapper
public interface NoncoreWordMapper extends DaoMapper<NoncoreWord, Long> {
	public List<Long> selectDisCreatorId();

	public List<NoncoreWord> pageList(Map<String, Object> paramMap);

	public Long pageCount(Map<String, Object> paramMap);

	public NoncoreWord selectNoncoreWordByWord(String word);
}