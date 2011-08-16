package com.baidu.rigel.unique.dao.xuanyuan;

import java.util.List;

import com.baidu.rigel.platform.dao.SqlMapDao;
import com.baidu.rigel.unique.bo.xuanyuan.CustContact;

public interface CustContactDao extends SqlMapDao<CustContact, Long> {
	public List<CustContact> selectCustContactByCustId(Long custId);
}