package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.bo.SeasonCustListPhone;
import com.baidu.rigel.unique.service.SeasonCustListPhoneService;
import com.baidu.rigel.unique.service.SeasonCustListService;
import com.baidu.rigel.unique.service.SeasonCustListVoService;
import com.baidu.rigel.unique.utils.Utils;
import com.baidu.rigel.unique.vo.SeasonCustListVo;

@Service("seasonCustListVoService")
public class SeasonCustListVoServiceImpl implements SeasonCustListVoService {

	@Autowired
	private SeasonCustListService seasonCustListService;
	@Autowired
	private SeasonCustListPhoneService seasonCustListPhoneService;

	public Long pageCount(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList) {
		return seasonCustListService.pageCount(custName, url, createId, phoneNum, useInvalidate, beginInvalidate, endInvalidate, posIdList);
	}

	public List<SeasonCustListVo> pageList(String custName, String url, Long createId, String phoneNum, Short useInvalidate, Date beginInvalidate, Date endInvalidate, List<Long> posIdList, int curPage, int pageSize) {
		List<SeasonCustList> seasonCustListList = seasonCustListService.pageList(custName, url, createId, phoneNum, useInvalidate, beginInvalidate, endInvalidate, posIdList, curPage, pageSize);
		if (CollectionUtils.isNotEmpty(seasonCustListList)) {
			List<SeasonCustListVo> seasonCustListVoList = new LinkedList<SeasonCustListVo>();
			for (SeasonCustList seasonCustList : seasonCustListList) {
				List<SeasonCustListPhone> seasonCustListPhoneList = seasonCustListPhoneService.selectSeasonCustListPhoneBySeasonCustListId(seasonCustList.getId());

				SeasonCustListVo seasonCustListVo = new SeasonCustListVo();
				seasonCustListVo.setSeasonCustList(seasonCustList);
				seasonCustListVo.setSeasonCustListPhoneList(seasonCustListPhoneList);
				seasonCustListVoList.add(seasonCustListVo);
			}
			return seasonCustListVoList;
		}
		return new ArrayList<SeasonCustListVo>(0);
	}

	public void delete(Long id) {
		SeasonCustList seasonCustList = seasonCustListService.findById(id);
		if (Utils.isNotNull(seasonCustList)) {
			List<SeasonCustListPhone> seasonCustListPhoneList = seasonCustListPhoneService.selectSeasonCustListPhoneBySeasonCustListId(id);
			// TODO Anders Zhu ： 优化，一次性update，不要分步update
			for (SeasonCustListPhone seasonCustListPhone : seasonCustListPhoneList) {
				seasonCustListPhoneService.deleteSeasonCustListPhone(seasonCustListPhone.getId());
			}
			seasonCustListService.deleteSeasonCustList(id);
		}
	}

	public SeasonCustListVo findSeasonCustListVo(Long id) {
		SeasonCustList seasonCustList = seasonCustListService.findById(id);
		if (Utils.isNotNull(seasonCustList)) {
			List<SeasonCustListPhone> seasonCustListPhoneList = seasonCustListPhoneService.selectSeasonCustListPhoneBySeasonCustListId(seasonCustList.getId());
			SeasonCustListVo seasonCustListVo = new SeasonCustListVo();
			seasonCustListVo.setSeasonCustList(seasonCustList);
			seasonCustListVo.setSeasonCustListPhoneList(seasonCustListPhoneList);
			return seasonCustListVo;
		}
		return null;
	}
}
