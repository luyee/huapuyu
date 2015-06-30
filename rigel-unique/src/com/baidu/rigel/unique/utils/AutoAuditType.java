package com.baidu.rigel.unique.utils;

/**
 * 自动审核结果类型
 * 
 * @author Anders Zhu 2011-7-7 上午11:46:11
 */
public enum AutoAuditType {
	/**
	 * 自动审核结果：通过（0）
	 */
	PASS("通过", (short) 0),
	/**
	 * 自动审核结果：质疑（1）
	 */
	OPPUGN("质疑", (short) 1),
	/**
	 * 自动审核结果：老户新开（2）
	 */
	OLD_TO_NEW("老户新开", (short) 2),
	/**
	 * 自动审核结果： 一户多开（3）
	 */
	ONE_TO_MANY(" 一户多开", (short) 3),
	/**
	 * 自动审核结果：重复（4）
	 */
	EXIST("重复", (short) 4),
	/**
	 * 自动审核结果：下一步（5）
	 */
	NEXT_STEP("下一步", (short) 5),
	/**
	 * 自动审核结果：季节性备案（6）
	 */
	SEASON_FILE("季节性备案", (short) 6),
	/**
	 * 自动审核结果：CSC下发疑似重复（7）
	 */
	CSC_EXIST("CSC下发疑似重复", (short) 7),
	/**
	 * 自动审核结果：公司名质疑（8）
	 */
	COMPANY_NAME_OPPUGN("公司名质疑", (short) 8);

	private Short value;
	private String label;

	private AutoAuditType(String label, Short value) {
		this.value = value;
		this.label = label;
	}

	public Short getValue() {
		return this.value;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
