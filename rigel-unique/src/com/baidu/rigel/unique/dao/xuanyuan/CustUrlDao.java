package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;

public interface CustUrlDao extends SqlMapDao<CustUrl, Long> {
	public List<CustUrl> selectCustUrlByCustId(Long custId);
}