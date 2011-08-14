package com.baidu.rigel.unique.service.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.xuanyuan.ShifenCustomer;

public interface ShifenCustomerService extends GenericService<ShifenCustomer, Long> {

	public List<Map<String, Object>> containSiteUrl(String siteUrl, int count);

	public List<Map<String, Object>> equalSiteUrl(String siteUrl);

	public List<Long> equalCompanyName(String companyName);

	public List<Map<String, Object>> equalUrlDomain(String urlDomain);

	public List<Map<String, Object>> containUrlDomain(String urlDomain);

	public List<ShifenCustomer> getShifenCustomerByCustIdList(List<Long> custIdList);

	public List<Long> selectCustIdByCompanyNameSiteUrl(String companyName, String siteUrl);
}
