package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;

@SqlMapper
public interface CustUrlMapper extends DaoMapper<CustUrl, Long> {
	public List<CustUrl> selectCustUrlByCustId(Long custId);
}