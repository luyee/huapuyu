package com.baidu.rigel.unique.utils;

/**
 * 期限类型
 * 
 * @author Anders Zhu
 */
public enum PeriodType {
	/**
	 * 短期（0）
	 */
	SHORT("短期", 0),
	/**
	 * 长期（1）
	 */
	LONG("长期", 1);

	private Integer value;
	private String label;

	private PeriodType(String label, Integer value) {
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
