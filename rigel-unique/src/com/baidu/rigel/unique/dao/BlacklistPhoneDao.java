package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.BlacklistPhone;

public interface BlacklistPhoneDao extends SqlMapDao<BlacklistPhone, Long> {
	public List<Long> selectIdByPhoneNum(String phoneNum);

	public List<BlacklistPhone> selectBlacklistPhoneByBlacklistId(Long blacklistId);

	public void deleteBlacklistPhone(Long id);
}