package com.baidu.rigel.unique.service;

import java.util.List;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.CustUrl;

public interface CustUrlService extends GenericService<CustUrl, Long> {
	public List<CustUrl> findCustUrlByCustId(Long custId);
}
