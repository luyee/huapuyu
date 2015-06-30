package com.baidu.rigel.unique.service;

import java.util.List;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.BlacklistPhone;

public interface BlacklistPhoneService extends GenericService<BlacklistPhone, Long> {

	public List<Long> equalPhoneNum(String phoneNum);

	public List<BlacklistPhone> selectBlacklistPhoneByBlacklistId(Long blacklistId);

	public void deleteBlacklistPhone(Long id);
}
