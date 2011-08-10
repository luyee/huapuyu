package com.baidu.rigel.unique.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.platform.dao.BaseSqlMapDao;
import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.dao.BlacklistPhoneDao;
import com.baidu.rigel.unique.dao.BlacklistPhoneMapper;

@Service("blacklistPhoneDao")
public class BlacklistPhoneDaoImpl extends BaseSqlMapDao<BlacklistPhone, Long> implements BlacklistPhoneDao {
	@Autowired
	private BlacklistPhoneMapper blacklistPhoneMapper;

	@Override
	public DaoMapper<BlacklistPhone, Long> getDaoMapper() {
		return blacklistPhoneMapper;
	}

	public List<Long> selectIdByPhoneNum(String phoneNum) {
		return blacklistPhoneMapper.selectIdByPhoneNum(phoneNum);
	}

	public List<BlacklistPhone> selectBlacklistPhoneByBlacklistId(Long blacklistId) {
		return blacklistPhoneMapper.selectBlacklistPhoneByBlacklistId(blacklistId);
	}

	public void deleteBlacklistPhone(Long id) {
		blacklistPhoneMapper.deleteBlacklistPhone(id);
	}
}