package com.baidu.rigel.unique.service;

import java.util.List;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;

public interface SeasonCustListPhoneService extends GenericService<SeasonCustListPhone, Long> {
	public List<SeasonCustListPhone> selectSeasonCustListPhoneBySeasonCustListId(Long seasonCustListId);

	public SeasonCustListPhone addSeasonCustListPhone(SeasonCustListPhone seasonCustListPhone);

	public void deleteSeasonCustListPhone(Long id);
}
