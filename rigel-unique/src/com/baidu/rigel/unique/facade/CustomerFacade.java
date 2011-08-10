package com.baidu.rigel.unique.facade;

import java.util.List;
import java.util.Map;

import com.baidu.rigel.unique.vo.CustomerVO;

/**
 * 盘古客户资料的远程服务接口
 * 
 * @author cm
 * @created 2010-9-21 下午04:41:37
 * @lastModified
 * @history
 */
public interface CustomerFacade {
	/**
	 * 根据url精确匹配,返回的map包含了cust_id,cust_type,cust_full_name,pos_id
	 * 
	 * @param url
	 * @return
	 * @author cm
	 * @created 2010-9-10 下午07:58:57
	 * @lastModified
	 * @history
	 */
	public List<Map<String, Object>> findByUrl(String url);

	/**
	 * 根据domain精确匹配,返回的map包含了cust_id,cust_type,cust_full_name,pos_id
	 * 
	 * @param domain
	 * @return
	 * @author cm
	 * @created 2010-9-10 下午07:59:25
	 * @lastModified
	 * @history
	 */
	public List<Map<String, Object>> findByDomain(String domain);

	/**
	 * 根据custname精确匹配,返回的map包含了cust_id,cust_type,cust_full_name,pos_id
	 * 
	 * @param custName
	 * @return
	 * @author cm
	 * @created 2010-9-10 下午07:59:44
	 * @lastModified
	 * @history
	 */
	public List<Map<String, Object>> findByCustName(String custName);

	/**
	 * 根据phone精确匹配
	 * 
	 * @param phone
	 * @return
	 * @author cm
	 * @created 2010-9-10 下午08:00:03
	 * @lastModified
	 * @history
	 */
	public List<Map<String, Object>> findByPhone(String phone);

	/**
	 * 使用url前置查询,返回的map包含了cust_id,cust_type,cust_full_name,pos_id
	 * 
	 * @param url
	 * @param count
	 * @return
	 * @author cm
	 * @created 2010-9-21 下午02:26:26
	 * @lastModified
	 * @history
	 */
	public List<Map<String, Object>> listPreMatchCustUrl(String url, int count);

	/**
	 * 使用主域前置查询,返回的map包含了cust_id,cust_type,cust_full_name,pos_id
	 * 
	 * @param url
	 * @param count
	 * @return
	 * @author cm
	 * @created 2010-9-21 下午02:26:26
	 * @lastModified
	 * @history
	 */
	public List<Map<String, Object>> listPreMatchCustDomain(String domain);

	/**
	 * 只得到客户资料的基本信息,不包括开户信息和选填信息
	 * 
	 * @param id
	 * @return
	 * @author cm
	 * @created 2010-9-8 下午02:55:10
	 * @lastModified
	 * @history
	 */
	public CustomerVO getCustomerSimpleInfo(Long id);

	/**
	 * 精确匹配代理广告公司分支名
	 * 
	 * @param custBranchName
	 * @param custType
	 * @return
	 * @author cm
	 * @created 2010-9-23 下午01:57:37
	 * @lastModified
	 * @history
	 */
	public List<Long> listMatchCustBranchName(String custBranchName, Long custType);

	/**
	 * 精确匹配客户名称
	 * 
	 * @param custFullName
	 * @return
	 * @author cm
	 * @created 2010-9-23 下午01:58:20
	 * @lastModified
	 * @history
	 */
	public List<Long> listMatchCustName(String custFullName);

	/**
	 * 根据custid数组得到custid和posid的映射
	 * 
	 * @param custId
	 * @return
	 * @author cm
	 * @created 2010-9-23 下午03:16:51
	 * @lastModified
	 * @history
	 */
	public Map<Long, Long> findCustPosIdById(Long... custIds);

	/**
	 * 
	 * 根据custid数组得到custid和fullname的映射
	 * 
	 * @param custIds
	 * @return
	 * @author cm
	 * @created 2010-9-23 下午03:34:11
	 * @lastModified
	 * @history
	 */
	public Map<Long, String> findCustNameById(Long... custIds);

	/**
	 * 根据id数组批量得到客户资料的基本信息,不包括开户信息和选填信息
	 * 
	 * @param id
	 * @return
	 * @author cm
	 * @created 2010-9-8 下午02:55:10
	 * @lastModified
	 * @history
	 */
	public Map<Long, CustomerVO> getCustomerSimpleInfos(Long... ids);

	/**
	 * 根据客户资料ID和岗位ID查询,排除custId,并且状态是有限制的只能为“新提交”、“确认中”、“跟进中”、“订单审核中”。
	 * 
	 * @param custIdList
	 * @param posid
	 * @param custId
	 * @return
	 * @author cm
	 * @created 2010-9-23 下午04:38:01
	 * @lastModified
	 * @history
	 */
	public boolean findByCustIdsAndPosid(List<Long> custIdList, Long posid, Long custId);

	/**
	 * 客户资料审核对比时,根据客户资料id的集合,得到资料ID,录入时间,分配时间,公司名称,状态,性质的map集合
	 * 
	 * @param custIdList
	 * @return
	 * @author cm
	 * @created 2010-9-25 上午11:04:39
	 * @lastModified
	 * @history
	 */
	public List<Map<String, Object>> findCustCompareInfoByCustIds(List<Long> custIdList);

	/**
	 * 轩辕系统的客户资料下发到盘古中
	 * 
	 * @param customerVO
	 * @param postils
	 * @return
	 * @author cm
	 * @created 2010-9-25 下午05:37:44
	 * @lastModified
	 * @history
	 */
	public boolean saveDistributeCust(CustomerVO customerVO, List<Map<String, Object>> postils);

}
