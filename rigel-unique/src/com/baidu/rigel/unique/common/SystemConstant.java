package com.baidu.rigel.unique.common;

public class SystemConstant extends Constant {
	/** 默认值--最大客户保有数 */
	public static final Long DEFAULT_MAX_OWNER_SIZE = 50L;
	/** 默认值--最大客户申领个数 */
	public static final Long DEFAULT_MAX_APPLY_SIZE = 40L;
	/** 默认值--最大客户保有天数 */
	public static final Long DEFAULT_MAX_RETAIN_DAYS = 90L;

	public static final Long DEFAULT_USELESS_ID = -1L;

	// public static final String BLACK_LIST_CUST_SELF="客户资料";

	// public static final String BLACK_LIST_CUST_SF="shifen账号";

	public static final Long BLACK_LIST_ERROR_NO_DUPBLACKLIST = 1L;
	public static final Long BLACK_LIST_ERROR_NO_DUPCUST = 0L;

}
