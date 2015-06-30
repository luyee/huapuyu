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
	SHORT("短期", (short) 0),
	/**
	 * 长期（1）
	 */
	LONG("长期", (short) 1);

	private Short value;
	private String label;

	private PeriodType(String label, Short value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return this.label;
	}

	public Short getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
