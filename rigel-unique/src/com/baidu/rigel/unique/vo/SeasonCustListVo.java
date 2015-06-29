package com.baidu.rigel.unique.vo;

import java.util.List;

import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;

public class SeasonCustListVo {
	private List<SeasonCustListPhone> seasonCustListPhoneList;
	private SeasonCustList seasonCustList;

	public List<SeasonCustListPhone> getSeasonCustListPhoneList() {
		return seasonCustListPhoneList;
	}

	public void setSeasonCustListPhoneList(List<SeasonCustListPhone> seasonCustListPhoneList) {
		this.seasonCustListPhoneList = seasonCustListPhoneList;
	}

	public SeasonCustList getSeasonCustList() {
		return seasonCustList;
	}

	public void setSeasonCustList(SeasonCustList seasonCustList) {
		this.seasonCustList = seasonCustList;
	}

}