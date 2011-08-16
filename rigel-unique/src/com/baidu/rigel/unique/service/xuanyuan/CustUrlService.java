package com.baidu.rigel.unique.service.xuanyuan;

import java.util.List;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;

public interface CustUrlService extends GenericService<CustUrl, Long> {
	public List<CustUrl> selectCustUrlByCustId(Long custId);
}
