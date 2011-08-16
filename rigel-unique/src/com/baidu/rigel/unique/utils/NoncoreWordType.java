package com.baidu.rigel.unique.utils;

public enum NoncoreWordType {
	/**
	 * 数字（1）
	 */
	NUMBER("数字", 1L),
	/**
	 * 字母（2）
	 */
	CHARACTER("字母", 2L),
	/**
	 * 特定词（3）
	 */
	WORD("特定词", 3L);

	private Long value;
	private String label;

	private NoncoreWordType(String label, Long value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return this.label;
	}

	public Long getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
