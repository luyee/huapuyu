package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.NoncoreWord;

public interface NoncoreWordDao extends SqlMapDao<NoncoreWord, Long> {
	public List<Long> selectDisCreatorId();

	// public List<NoncoreWord> pageList(CoreWordQueryInfo queryInfo);

	// public Long pageCount(CoreWordQueryInfo queryInfo);

	public Long pageCount(Long creatorId, String word);

	public List<NoncoreWord> pageList(Long creatorId, String word, int curPage, int pageSize);

	public NoncoreWord selectNoncoreWordByWord(String word);
}