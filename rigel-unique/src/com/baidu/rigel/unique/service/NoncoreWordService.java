package com.baidu.rigel.unique.service;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.NoncoreWord;
import com.baidu.rigel.unique.common.CoreWordQueryInfo;

public interface NoncoreWordService extends GenericService<NoncoreWord, Long> {
	public List<Map<String, Object>> getCoreWordUserListForFE();

	public List<Long> selectDisCreatorId();

	public List<NoncoreWord> getCoreWordListForView(CoreWordQueryInfo queryInfo);

	public Long getCoreWordCntForView(CoreWordQueryInfo queryInfo);

	public List<NoncoreWord> pageList(Long creatorId, String word, int curPage, int pageSize);

	public Long pageCount(Long creatorId, String word);

	public NoncoreWord selectNoncoreWordByWord(String word);

	public boolean saveNoncoreWord(NoncoreWord noncoreWord);
}