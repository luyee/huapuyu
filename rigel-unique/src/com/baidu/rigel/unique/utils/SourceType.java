package com.baidu.rigel.unique.utils;

/**
 * 数据来源类型
 * 
 * @author Anders Zhu
 */
public enum SourceType {
	/**
	 * 销售
	 */
	CUST_SOURCE_SALE {
		@Override
		public String toString() {
			return "custSourceSale";
		}
	},
	/**
	 * 十分
	 */
	CUST_SOURCE_SHIFEN {
		@Override
		public String toString() {
			return "custSourceShifen";
		}
	},
	/**
	 * 黑名单
	 */
	CUST_SOURCE_BLACKLIST {
		@Override
		public String toString() {
			return "custSourceBlacklist";
		}
	}
}
