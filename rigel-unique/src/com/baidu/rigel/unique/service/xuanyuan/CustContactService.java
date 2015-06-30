package com.baidu.rigel.unique.service.xuanyuan;

import java.util.List;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.xuanyuan.CustContact;

public interface CustContactService extends GenericService<CustContact, Long> {
	public List<CustContact> selectCustContactByCustId(Long custId);
}
