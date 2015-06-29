package com.baidu.rigel.unique.common;

import com.baidu.rigel.platform.vo.VO;

/**
 * 非核心词查询条件
 * 
 * @author ljq
 * 
 */
public class CoreWordQueryInfo extends VO {
	// 特定词
	private String coreWord;
	// 设定人id
	private Long addUserId;

	// 数据起始位置
	private Long dataLimitFrom;

	// 数据长度
	private Long dataLimitLength;

	public String getCoreWord() {
		return coreWord;
	}

	public void setCoreWord(String coreWord) {
		this.coreWord = coreWord;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public Long getDataLimitFrom() {
		return dataLimitFrom;
	}

	public void setDataLimitFrom(Long dataLimitFrom) {
		this.dataLimitFrom = dataLimitFrom;
	}

	public Long getDataLimitLength() {
		return dataLimitLength;
	}

	public void setDataLimitLength(Long dataLimitLength) {
		this.dataLimitLength = dataLimitLength;
	}

}
