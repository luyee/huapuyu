package com.anders.crm.utils;

/**
 * 角色类型
 * 
 * @author Anders Zhu
 * 
 */
public enum RoleType {
	/*
	 * 系统管理员角色
	 */
	ROLE_ADMIN(1L),
	/**
	 * 个人用户角色
	 */
	ROLE_USER(2L);

	private Long value;

	private RoleType(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}
}
