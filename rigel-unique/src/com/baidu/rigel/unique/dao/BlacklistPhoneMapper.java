package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.BlacklistPhone;

@SqlMapper
public interface BlacklistPhoneMapper extends DaoMapper<BlacklistPhone, Long> {
	public List<Long> selectIdByPhoneNum(String phoneNum);

	public List<BlacklistPhone> selectBlacklistPhoneByBlacklistId(Long blacklistId);

	public void deleteBlacklistPhone(Long id);
}