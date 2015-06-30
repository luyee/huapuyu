package com.anders.hibernate.model.crm;

/**
 * 联系方式类型
 * 
 * @author Anders Zhu
 * 
 */
public enum ContactInfoType {
	/**
	 * 座机：1
	 */
	PHONE((byte) 1),
	/**
	 * 手机：2
	 */
	MOBILE((byte) 2),
	/**
	 * 传真：3
	 */
	FAX((byte) 3),
	/**
	 * 邮箱：4
	 */
	EMAIL((byte) 4),
	/**
	 * QQ：5
	 */
	QQ((byte) 5),
	/**
	 * 微信：6
	 */
	WECHAT((byte) 6),
	/**
	 * 微博：7
	 */
	WEIBO((byte) 7),
	/**
	 * LinkedIn：8
	 */
	LINKEDIN((byte) 8),
	/**
	 * Skype：9
	 */
	SKYPE((byte) 9),
	/**
	 * MSN：10
	 */
	MSN((byte) 10);

	private byte type;

	private ContactInfoType(byte type) {
		this.type = type;
	}

	public byte getType() {
		return type;
	}
}
