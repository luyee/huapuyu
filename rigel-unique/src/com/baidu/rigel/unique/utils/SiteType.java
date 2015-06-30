package com.baidu.rigel.unique.utils;

/**
 * 客户建站类型
 * 
 * @author Anders Zhu
 */
public enum SiteType {
	/**
	 * 已有网站（0）
	 */
	HAVE("已有网站", 0),
	/**
	 * 没有网站（1）
	 */
	NONE("没有网站", 1),
	/**
	 * 未知（2）
	 */
	UNKNOWN("未知", 2);

	private Integer value;
	private String label;

	private SiteType(String label, Integer value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return this.label;
	}

	public Integer getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
