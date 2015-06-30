package com.baidu.rigel.unique.service;

import java.util.List;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.AreaWord;

public interface AreaWordService extends GenericService<AreaWord, Long> {
	public List<AreaWord> selectAllOrderByCWord();

	public void deleteAreaWord(Long id);
}