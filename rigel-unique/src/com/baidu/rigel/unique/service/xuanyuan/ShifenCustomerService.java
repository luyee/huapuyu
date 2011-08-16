package com.baidu.rigel.unique.service.xuanyuan;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.platform.service.GenericService;
import com.baidu.rigel.unique.bo.xuanyuan.ShifenCustomer;

public interface ShifenCustomerService extends GenericService<ShifenCustomer, Long> {

	public List<Map<String, Object>> selectCustIdNamesLikeBySiteUrl(String siteUrl, int count);

	public List<Map<String, Object>> selectCustIdNamesBySiteUrl(String siteUrl);

	public List<Long> selectCustIdByCompanyName(String companyName);

	public List<Map<String, Object>> selectCustIdNamesByUrlDomain(String urlDomain);

	public List<Map<String, Object>> selectCustIdNamesLikeByUrlDomain(String urlDomain);

	public List<ShifenCustomer> selectShifenCustomerByCustIdList(List<Long> custIdList);

	public List<Long> selectCustIdByCompanyNameSiteUrl(String companyName, String siteUrl);

	public boolean isSiteUrlExist(String siteUrl);
}
