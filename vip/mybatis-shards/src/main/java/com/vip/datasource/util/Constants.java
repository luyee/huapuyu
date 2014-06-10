package com.vip.datasource.util;

public interface Constants {
	/**
	 * write key
	 */
	public final static String WRITE_KEY = "WRITE";
	/**
	 * read key
	 */
	public final static String READ_KEY = "READ";

	// TODO Anders MySQL什么版本开始支持dual
	/**
	 * validate sql
	 */
	public final static String VALIDATE_SQL = "SELECT 1 FROM dual";

	/**
	 * default dynamic datasource
	 */
	public final static String DEFAULT_DYNAMIC_DS = "defaultDataSource";
}
