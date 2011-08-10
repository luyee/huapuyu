package com.baidu.rigel.unique.vo;

import java.util.List;

import com.baidu.rigel.unique.bo.Blacklist;
import com.baidu.rigel.unique.bo.BlacklistPhone;

public class BlacklistVo {
	private List<BlacklistPhone> blacklistPhoneList;
	private Blacklist blacklist;

	public List<BlacklistPhone> getBlacklistPhoneList() {
		return blacklistPhoneList;
	}

	public void setBlacklistPhoneList(List<BlacklistPhone> blacklistPhoneList) {
		this.blacklistPhoneList = blacklistPhoneList;
	}

	public Blacklist getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}

}