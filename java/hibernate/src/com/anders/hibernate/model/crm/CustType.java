package com.anders.hibernate.model.crm;

/**
 * 客户类型
 * 
 * @author Anders Zhu
 * 
 */
public enum CustType {
	/**
	 * 个人客户：1
	 */
	INDIVIDUAL((byte) 1),
	/**
	 * 企业客户：2
	 */
	ENTERPRISE((byte) 2);

	private byte type;

	private CustType(byte type) {
		this.type = type;
	}

	public byte getType() {
		return type;
	}
}
