package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;

import com.baidu.rigel.platform.dao.DaoMapper;
import com.baidu.rigel.platform.dao.SqlMapper;
import com.baidu.rigel.unique.bo.xuanyuan.CustContact;

@SqlMapper
public interface CustContactMapper extends DaoMapper<CustContact, Long> {
	public List<CustContact> selectCustContactByCustId(Long custId);
}