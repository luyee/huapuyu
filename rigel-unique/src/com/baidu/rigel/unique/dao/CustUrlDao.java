package com.baidu.rigel.unique.dao;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.CustUrl;

public interface CustUrlDao extends SqlMapDao<CustUrl, Long> {
	public List<CustUrl> selectCustUrlByCustId(Long custId);
}