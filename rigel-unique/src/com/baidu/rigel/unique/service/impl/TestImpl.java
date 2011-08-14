package com.baidu.rigel.unique.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.rigel.service.tinyse.data.SaleData;
import com.baidu.rigel.service.tinyse.data.ShifenData;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.service.usercenter.service.UserMgr;
import com.baidu.rigel.unique.bo.BlacklistPhone;
import com.baidu.rigel.unique.bo.SeasonCustList;
import com.baidu.rigel.unique.bo.ShifenCustWhiteList;
import com.baidu.rigel.unique.bo.pangu.Cust;
import com.baidu.rigel.unique.bo.xuanyuan.CustContact;
import com.baidu.rigel.unique.bo.xuanyuan.CustUrl;
import com.baidu.rigel.unique.bo.xuanyuan.Customer;
import com.baidu.rigel.unique.bo.xuanyuan.Phone;
import com.baidu.rigel.unique.bo.xuanyuan.ShifenCustomer;
import com.baidu.rigel.unique.exception.AutoAuditException;
import com.baidu.rigel.unique.facade.AuditInfo;
import com.baidu.rigel.unique.facade.AutoAuditRecord;
import com.baidu.rigel.unique.service.AuditService;
import com.baidu.rigel.unique.service.BlacklistPhoneService;
import com.baidu.rigel.unique.service.BlacklistService;
import com.baidu.rigel.unique.service.SeasonCustListService;
import com.baidu.rigel.unique.service.ShifenCustWhiteListService;
import com.baidu.rigel.unique.service.Test;
import com.baidu.rigel.unique.service.UrlWhitelistService;
import com.baidu.rigel.unique.service.pangu.CustService;
import com.baidu.rigel.unique.service.xuanyuan.CustUrlService;
import com.baidu.rigel.unique.service.xuanyuan.CustomerService;
import com.baidu.rigel.unique.service.xuanyuan.FollowAssignService;
import com.baidu.rigel.unique.service.xuanyuan.ShifenCustomerService;
import com.baidu.rigel.unique.tinyse.TinyseMgr;
import com.baidu.rigel.unique.utils.AutoAuditSourceType;
import com.baidu.rigel.unique.utils.AutoAuditType;
import com.baidu.rigel.unique.utils.BusiUtils;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.CustType;
import com.baidu.rigel.unique.utils.FieldConstant;
import com.baidu.rigel.unique.utils.FlagType;
import com.baidu.rigel.unique.utils.URLUtils;
import com.baidu.rigel.unique.utils.Utils;
import com.baidu.rigel.unique.utils.Constant.ValidType;
import com.baidu.rigel.unique.vo.CustContactPhoneVO;
import com.baidu.rigel.unique.vo.CustContactVO;
import com.baidu.rigel.unique.vo.CustomerVO;

@Service("test")
public class TestImpl implements Test {
	private static Log log = LogFactory.getLog(TestImpl.class);

	@Autowired
	private TinyseMgr tinyseMgr;
	@Autowired
	private UserMgr userMgr;
	@Autowired
	private CustService custService;
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private BlacklistPhoneService blacklistPhoneService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private FollowAssignService followAssignService;
	@Autowired
	private ShifenCustomerService shifenCustomerService;
	@Autowired
	private CustUrlService custUrlAuditService;
	@Autowired
	private SeasonCustListService seasonCustListService;
	@Autowired
	private ShifenCustWhiteListService shifenCustWhiteListService;
	@Autowired
	private UrlWhitelistService urlWhitelistService;
	@Autowired
	private AuditService auditService;

	private String companyComparePattern;
	private List<Long> panguPosIdList = null;

	/**
	 * 模糊匹配客户电话号码
	 * 
	 * @param phoneArea
	 * @param phoneNumber
	 *            客户电话号码
	 * @param count
	 *            指定记录数
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> containContactPhone(String phoneAreaCode, String phoneNumber, int count) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		// 判断
		if (Utils.isNull(phoneNumber)) {
			log.warn(LogEqualToNull("phoneNumber"));
			return returnList;
		} else if (Utils.isEqualLessThanZero(count)) {
			log.warn(LogLessEqualThanZero("count"));
			return returnList;
		} else if (StringUtils.isNotBlank(phoneAreaCode)) {
			phoneNumber = phoneAreaCode + Constant.HYPHEN + phoneNumber;
		}

		// 十分
		List<ShifenData> shifenList = tinyseMgr.queryShifenDataWithWordPhrase(phoneNumber, count);
		for (ShifenData shifenData : shifenList) {
			String name = BusiUtils.getCustName(shifenData.getRealcompanyname(), shifenData.getCompanyname(), shifenData.getCustomername());
			returnList.add(BusiUtils.getIdNameSourceMapFromShifen(shifenData.getId(), name));
		}

		return Utils.limitList(returnList, count);
	}

	/**
	 * 精确匹配客户电话号码
	 * 
	 * @param phoneArea
	 * @param phoneNumber
	 *            客户电话号码
	 * @param count
	 *            指定记录数
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> equalContactPhone(String phoneAreaCode, String phoneNumber, int count) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		// 判断
		if (Utils.isNull(phoneNumber)) {
			log.warn(LogEqualToNull("phoneNumber"));
			return returnList;
		} else if (Utils.isEqualLessThanZero(count)) {
			log.warn(LogLessEqualThanZero("count"));
			return returnList;
		}

		// 销售
		List<Map<String, Object>> saleDataList = auditService.listMatchPhoneContactMap(phoneAreaCode, phoneNumber);
		for (Map<String, Object> map : saleDataList)
			returnList.add(BusiUtils.getIdNameSourceMapFromSale(map.get(FieldConstant.CUST_ID), map.get(FieldConstant.CUST_FULL_NAME)));

		return Utils.limitList(returnList, count);
	}

	/**
	 * 模糊匹配客户名称（根据客户名称从黑名单中获取记录，当记录数小于count时，需要从销售甚至十分中来补足，如果仍然没有补足，则重新组合custName再依次从黑名单、销售和十分中补足），匹配顺序为黑名单->销售->十分
	 * 
	 * @param custName
	 *            客户名称
	 * @param custBranchName
	 * @param count
	 *            指定记录数
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> containCustNameStartFromBlacklist(String custName, String custBranchName, int count) {

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		// 判断
		if (Utils.isNull(custName)) {
			log.warn(LogEqualToNull("custName"));
			return returnList;
		} else if (Utils.isEqualLessThanZero(count)) {
			log.warn(LogLessEqualThanZero("count"));
			return returnList;
		}

		// 黑名单
		Set<Long> blacklistSet = new HashSet<Long>();
		// 销售
		Set<Long> saleSet = new HashSet<Long>();
		// 十分
		Set<Long> shifenSet = new HashSet<Long>();

		// 黑名单
		returnList.addAll(containCustNameFromBlacklist(custName, blacklistSet, Boolean.FALSE));

		// 如果已经获取到指定数目的记录则返回，否则继续从销售中获取
		if (returnList.size() >= count)
			return Utils.limitList(returnList, count);

		// 销售
		returnList.addAll(containCustNameFromSale(custName, count, saleSet, Boolean.FALSE, Boolean.FALSE));

		// 如果已经获取到指定数目的记录则返回，否则继续从十分中获取
		if (returnList.size() >= count)
			return Utils.limitList(returnList, count);

		// 十分
		returnList.addAll(containCustNameFromShifen(custName, count, shifenSet, Boolean.FALSE));

		// 如果已经获取到指定数目的记录则返回，否则重新组合custName（custName=custName+custBranchName）继续依次从黑名单、销售和十分中获取
		if (returnList.size() >= count)
			return Utils.limitList(returnList, count);

		if (StringUtils.isNotEmpty(custBranchName)) {
			custName += custBranchName;

			// 黑名单
			returnList.addAll(containCustNameFromBlacklist(custName, blacklistSet, Boolean.TRUE));

			// 如果已经获取到指定数目的记录则返回，否则继续从销售中获取
			if (returnList.size() >= count)
				return Utils.limitList(returnList, count);

			// 销售
			returnList.addAll(containCustNameFromSale(custName, count, saleSet, Boolean.TRUE, Boolean.FALSE));

			// 如果已经获取到指定数目的记录则返回，否则继续从十分中获取
			if (returnList.size() >= count)
				return Utils.limitList(returnList, count);

			// 十分
			returnList.addAll(containCustNameFromShifen(custName, count, shifenSet, Boolean.TRUE));
		}

		return Utils.limitList(returnList, count);
	}

	/**
	 * 模糊匹配客户名称（根据客户名称从销售中获取记录，当记录数小于count时，需要从黑名单甚至十分中来补足，如果仍然没有补足，则重新组合custName再依次从销售、黑名单和十分中补足），匹配顺序为销售->黑名单->十分
	 * 
	 * @param custName
	 *            客户名称
	 * @param custBranchName
	 * @param count
	 *            指定记录数
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> containCustNameStartFromSale(String custName, String custBranchName, int count) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		// 判断
		// TODO Anders Zhu ： 和containCustNameStartFromBlacklist的判断有所不同，没有判断count
		if (Utils.isNull(custName)) {
			log.warn(LogEqualToNull("custName"));
			return returnList;
		}

		// 销售
		Set<Long> saleSet = new HashSet<Long>();
		// 黑名单
		Set<Long> blacklistSet = new HashSet<Long>();
		// 十分
		Set<Long> shifenSet = new HashSet<Long>();

		// 销售
		returnList.addAll(containCustNameFromSale(custName, count, saleSet, Boolean.FALSE, Boolean.TRUE));

		// 如果已经获取到指定数目的记录则返回，否则继续从黑名单中获取
		if (returnList.size() >= count) {
			return Utils.limitList(returnList, count);
		}

		// 黑名单
		returnList.addAll(containCustNameFromBlacklist(custName, blacklistSet, Boolean.FALSE));

		// 如果已经获取到指定数目的记录则返回，否则继续从十分中获取
		if (returnList.size() >= count) {
			return Utils.limitList(returnList, count);
		}

		// 十分
		returnList.addAll(containCustNameFromShifen(custName, count, shifenSet, Boolean.FALSE));

		// 如果已经获取到指定数目的记录则返回，否则重新组合custName（custName=custName+custBranchName）继续依次从销售、黑名单和十分中获取
		if (returnList.size() >= count) {
			return Utils.limitList(returnList, count);
		}

		if (StringUtils.isNotEmpty(custBranchName)) {
			custName += custBranchName;

			// 销售
			returnList.addAll(containCustNameFromSale(custName, count, saleSet, Boolean.TRUE, Boolean.TRUE));

			// 如果已经获取到指定数目的记录则返回，否则继续从黑名单中获取
			if (returnList.size() >= count) {
				return Utils.limitList(returnList, count);
			}

			// 黑名单
			returnList.addAll(containCustNameFromBlacklist(custName, blacklistSet, Boolean.TRUE));

			// 如果已经获取到指定数目的记录则返回，否则继续从十分中获取
			if (returnList.size() >= count) {
				return Utils.limitList(returnList, count);
			}

			// 十分
			returnList.addAll(containCustNameFromShifen(custName, count, shifenSet, Boolean.TRUE));
		}

		return Utils.limitList(returnList, count);
	}

	/**
	 * 精确匹配客户名称（根据客户名称从黑名单中获取记录，当记录数大于0时，则返回，否则继续依次从销售和十分中获取记录），匹配顺序为黑名单->销售->十分
	 * 
	 * @param custFullName
	 *            客户名称
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> equalCustNameStartFromBlacklist(String custFullName) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		if (Utils.isNull(custFullName)) {
			log.warn(LogEqualToNull("custFullName"));
			return returnList;
		}

		// 黑名单
		List<Long> blacklistList = blacklistService.equalCompanyName(custFullName);
		for (Long id : blacklistList)
			returnList.add(BusiUtils.getIdNameSourceMapFromBlacklist(id, custFullName));

		if (Utils.isGreaterThanZero(returnList.size()))
			return returnList;

		// 销售
		List<Long> saleList = customerService.equalCustFullName(custFullName);
		saleList.addAll(custService.findByFullName(custFullName));
		for (Long id : saleList)
			returnList.add(BusiUtils.getIdNameSourceMapFromSale(id, custFullName));

		if (Utils.isGreaterThanZero(returnList.size()))
			return returnList;

		// 十分
		List<Long> shifenList = shifenCustomerService.equalCompanyName(custFullName);
		for (Long id : shifenList)
			returnList.add(BusiUtils.getIdNameSourceMapFromShifen(id, custFullName));

		return returnList;
	}

	/**
	 * 精确匹配客户名称（根据客户名称从销售中获取记录，当记录数大于0时，则返回，否则继续依次从黑名单和十分中获取记录），匹配顺序为销售->黑名单->十分
	 * 
	 * @param custFullName
	 *            客户名称
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> equalCustNameStartFromSale(String custFullName) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		if (Utils.isNull(custFullName)) {
			log.warn(LogEqualToNull("custFullName"));
			return returnList;
		}

		// 销售
		List<Long> saleIdList = customerService.equalCustFullName(custFullName);
		saleIdList.addAll(custService.findByFullName(custFullName));
		for (Long id : saleIdList)
			if (isBlackOrOld(id))
				returnList.add(BusiUtils.getIdNameSourceMapFromSale(id, custFullName));

		if (Utils.isGreaterThanZero(returnList.size()))
			return returnList;

		// 黑名单
		List<Long> blacklistList = blacklistService.equalCompanyName(custFullName);
		for (Long id : blacklistList)
			returnList.add(BusiUtils.getIdNameSourceMapFromBlacklist(id, custFullName));

		if (Utils.isGreaterThanZero(returnList.size()))
			return returnList;

		// 十分
		List<Long> shifenList = shifenCustomerService.equalCompanyName(custFullName);
		for (Long id : shifenList)
			returnList.add(BusiUtils.getIdNameSourceMapFromShifen(id, custFullName));

		return returnList;
	}

	/**
	 * 模糊匹配客户URL
	 * 
	 * @param custUrl
	 *            客户URL
	 * @param count
	 *            指定记录数
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> containCustUrl(String custUrl, int count) {

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		if (Utils.isNull(custUrl)) {
			log.warn(LogEqualToNull("custUrl"));
			return returnList;
		} else if (Utils.isEqualLessThanZero(count)) {
			log.warn(LogLessEqualThanZero("count"));
			return returnList;
		}

		// 销售
		List<Map<String, Object>> saleDataList = auditService.listPreMatchCustUrl(custUrl, count);
		for (Map<String, Object> map : saleDataList) {
			returnList.add(BusiUtils.getIdNameSourceMapFromSale(map.get("cust_id"), map.get("cust_full_name")));
		}

		// 十分
		List<Map<String, Object>> shifenDataList = shifenCustomerService.containSiteUrl(custUrl, count);
		for (Map<String, Object> map : shifenDataList) {
			String name = BusiUtils.getCustName((String) map.get("realcompanyname"), (String) map.get("companyname"), (String) map.get("customername"));
			returnList.add(BusiUtils.getIdNameSourceMapFromShifen(map.get("customerd"), name));
		}

		return Utils.limitList(returnList, count);
	}

	/**
	 * 精确匹配客户URL
	 * 
	 * @param custUrl
	 *            客户URL
	 * @param count
	 *            指定记录数
	 * @return 返回匹配结果列表
	 */
	public List<Map<String, Object>> equalCustUrl(String custUrl, int count) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		if (Utils.isNull(custUrl)) {
			log.warn(LogEqualToNull("custUrl"));
			return returnList;
		} else if (Utils.isEqualLessThanZero(count)) {
			log.warn(LogLessEqualThanZero("count"));
			return returnList;
		}

		// 销售
		List<Map<String, Object>> saleDataList = auditService.listMatchCustUrl(custUrl);
		for (Map<String, Object> map : saleDataList) {
			returnList.add(BusiUtils.getIdNameSourceMapFromSale(map.get("cust_id"), map.get("cust_full_name")));
		}

		// TODO Anders Zhu : 貌似有问题，没有进行补足
		if (Utils.isGreaterThanZero(returnList.size())) {
			return Utils.limitList(returnList, count);
		}

		// 十分
		List<Map<String, Object>> shifenDataList = shifenCustomerService.equalSiteUrl(custUrl);
		for (Map<String, Object> map : shifenDataList) {
			String name = BusiUtils.getCustName((String) map.get("realcompanyname"), (String) map.get("companyname"), (String) map.get("customername"));
			returnList.add(BusiUtils.getIdNameSourceMapFromShifen(map.get("customerd"), name));
		}

		return Utils.limitList(returnList, count);
	}

	/**
	 * 对称谓进行判断
	 * 
	 * @param name
	 *            客户名
	 * @return 判断结果
	 */
	public boolean validateCustName(String name) {
		if (StringUtils.isBlank(name))
			return Boolean.FALSE;

		name = name.trim();

		// TODO Anders Zhu : 修改，从配置文件中获取并且缓存住
		// TODO Anders Zhu : 查看蔡敏的代码
		String[] titleArray = Constant.TITLE.split(",");

		for (String title : titleArray)
			if (name.endsWith(title))
				return Boolean.FALSE;

		return Boolean.TRUE;
	}

	/**
	 * 判断URL是否存在老客户表中
	 * 
	 * @param url
	 *            URL
	 * @return 如果存在，返回true，否则返回false
	 */
	public boolean checkUrlInShifenCust(String url) {
		if (StringUtils.isEmpty(url))
			return Boolean.FALSE;

		List<Map<String, Object>> sfList = shifenCustomerService.equalSiteUrl(url);
		if (CollectionUtils.isNotEmpty(sfList))
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	/** **************************** 以下为私有辅助方法 ***************************** */

	/**
	 * 判断custId的客户在sale中是否是老客户黑名单
	 * 
	 * @param custId
	 * @return
	 */
	// TODO Anders Zhu : ThreadLocal
	private final ThreadLocal<Map<Long, Map<String, Object>>> cachedCustMap = new ThreadLocal<Map<Long, Map<String, Object>>>();

	/**
	 * 模糊匹配客户名称（黑名单）
	 * 
	 * @param custName
	 *            客户名称
	 * @param blacklistSet
	 *            存储记录中的客户编号，结合checkExist进行判断
	 * @param checkExist
	 *            是否要检查blacklistSet中包含新添加记录（true：是；false：否）
	 * @return 返回匹配结果列表
	 */
	private List<Map<String, Object>> containCustNameFromBlacklist(String custName, Set<Long> blacklistSet, boolean checkExist) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> blacklistList = blacklistService.containCompanyName(custName);
		for (Map<String, Object> blacklistMap : blacklistList) {
			if (checkExist) {
				if (!blacklistSet.contains(blacklistMap.get("blacklist_id")))
					returnList.add(BusiUtils.getIdNameSourceMapFromBlacklist(blacklistMap.get("blacklist_id"), blacklistMap.get("company_name")));
			} else {
				blacklistSet.add((Long) blacklistMap.get("blacklist_id"));
				returnList.add(BusiUtils.getIdNameSourceMapFromBlacklist(blacklistMap.get("blacklist_id"), blacklistMap.get("company_name")));
			}
		}
		return returnList;
	}

	/**
	 * 模糊匹配客户名称（销售）
	 * 
	 * @param custName
	 *            客户名称
	 * @param count
	 *            指定记录数
	 * @param saleSet
	 *            存储记录中的客户编号，结合checkExist进行判断
	 * @param checkExist
	 *            是否要检查saleSet中包含新添加记录（true：是；false：否）
	 * @param checkBlackOrOld
	 *            是否要检查客户属于黑名单和老客户（true：是；false：否）
	 * @return 返回匹配结果列表
	 */
	// TODO Anders Zhu ：改进
	private List<Map<String, Object>> containCustNameFromSale(String custName, int count, Set<Long> saleSet, boolean checkExist, boolean checkBlackOrOld) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List<SaleData> saleDataList = tinyseMgr.querySaleDataWithHold(custName, count);
		for (SaleData saleData : saleDataList) {
			if (checkBlackOrOld) {
				if (isBlackOrOld(saleData.getId())) {
					if (checkExist) {
						if (!saleSet.contains(saleData.getId()))
							returnList.add(BusiUtils.getIdNameSourceMapFromSale(saleData.getId(), saleData.getCompanyname()));
					} else {
						saleSet.add(saleData.getId());
						returnList.add(BusiUtils.getIdNameSourceMapFromSale(saleData.getId(), saleData.getCompanyname()));
					}
				}
			} else {
				if (checkExist) {
					if (!saleSet.contains(saleData.getId()))
						returnList.add(BusiUtils.getIdNameSourceMapFromSale(saleData.getId(), saleData.getCompanyname()));
				} else {
					saleSet.add(saleData.getId());
					returnList.add(BusiUtils.getIdNameSourceMapFromSale(saleData.getId(), saleData.getCompanyname()));
				}
			}
		}
		return returnList;
	}

	/**
	 * 模糊匹配客户名称（十分）
	 * 
	 * @param custName
	 *            客户名称
	 * @param count
	 *            指定记录数
	 * @param shifenSet
	 *            存储记录中的客户编号，结合checkExist进行判断
	 * @param checkExist
	 *            是否要检查shifenSet中包含新添加记录（true：是；false：否）
	 * @return 返回匹配结果列表
	 */
	private List<Map<String, Object>> containCustNameFromShifen(String custName, int count, Set<Long> shifenSet, boolean checkExist) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List<ShifenData> shifenDataList = tinyseMgr.queryShifenDataWithHold(custName, count);
		for (ShifenData shifenData : shifenDataList) {
			if (checkExist) {
				if (!shifenSet.contains(shifenData.getId())) {
					String name = BusiUtils.getCustName(shifenData.getRealcompanyname(), shifenData.getCompanyname(), shifenData.getCustomername());
					returnList.add(BusiUtils.getIdNameSourceMapFromShifen(shifenData.getId(), name));
				}
			} else {
				shifenSet.add(shifenData.getId());
				String name = BusiUtils.getCustName(shifenData.getRealcompanyname(), shifenData.getCompanyname(), shifenData.getCustomername());
				returnList.add(BusiUtils.getIdNameSourceMapFromShifen(shifenData.getId(), name));
			}
		}
		return returnList;
	}

	/**
	 * 判断custId的客户在sale中是否是老客户黑名单
	 * 
	 * @param custId
	 *            客户编号
	 * @return
	 */
	// TODO Anders Zhu : 重构
	private boolean isBlackOrOld(Long custId) {
		Map<String, Object> map = cachedCustMap.get().get(custId);
		if (null != map) {
			return (Boolean) map.get("isSigned");
		}
		if (BusiUtils.isPanguCustId(custId)) {
			Cust cust = custService.findById(custId);
			if (cust != null) {
				if (cust.getStat1().equals(Constant.PANGU_STAT1_CONTRACT_07)) {
					return Boolean.TRUE;
				} else {
					return Boolean.FALSE;
				}
			}
		} else {
			Customer customer = customerService.findById(custId);
			if (customer != null) {
				// 从中筛选中已经成单的客户,作为老客户
				if (customer.getCustStat1().equals(Constant.CUST_STAT_1_5) && customer.getCustStat2().equals(Constant.CUST_STAT_2_50)) {
					return Boolean.TRUE;
				}

				// 从中筛选出已经被标记为黑名单的客户
				if (customer.getBlackFlag().equals(Constant.BLACK_FLAG_Y)) {
					return Boolean.TRUE;
				}
			}
			Cust cust = custService.findById(custId);
			if (cust != null) {
				if (cust.getStat1().equals(Constant.PANGU_STAT1_CONTRACT_07)) {
					return Boolean.TRUE;
				} else {
					return Boolean.FALSE;
				}
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 黑名单审核（根据传入的参数对客户名称、电话号码或者URL进行判断）
	 * 
	 * @param validType
	 *            判断类型（客户名称、电话号码或者URL）
	 * @param value
	 * @return 自动验证结果
	 */
	private AutoAuditRecord auditBlacklist(ValidType validType, String value) {
		if (Utils.isNull(value)) {
			switch (validType) {
			case URL:
				log.warn(LogEqualToNull("url"));
				break;
			case PHONE:
				log.warn(LogEqualToNull("phone"));
				break;
			case CUSTNAME:
				log.warn(LogEqualToNull("custName"));
				break;
			}
			return null;
		}

		AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
		autoAuditRecord.setAutoAuditType(AutoAuditType.NEXT_STEP.getValue());
		if (StringUtils.isNotEmpty(value)) {
			List<Long> idList = blacklistService.equalCompanyName(value);
			switch (validType) {
			case URL:
				idList = blacklistService.equalUrl(value);
				break;
			case PHONE:
				idList = blacklistPhoneService.equalPhoneNum(value);
				break;
			case CUSTNAME:
				idList = blacklistService.equalCompanyName(value);
				break;
			}
			if (CollectionUtils.isNotEmpty(idList)) {
				AuditInfo auditInfo = BusiUtils.newAuditInfo(idList.get(0), null, AutoAuditSourceType.BLACKLIST.getValue(), null);
				List<AuditInfo> auditInfoList = new ArrayList<AuditInfo>();
				// TODO Anders Zhu : 重构
				switch (validType) {
				case URL:
					auditInfo.setName(blacklistService.findById(idList.get(0)).getCompanyName());
					BusiUtils.updateAuditInfo(auditInfo, null, null, null, "URL [" + value + "]");
					auditInfoList.add(auditInfo);
					BusiUtils.updateAutoAuditRecord(autoAuditRecord, AutoAuditType.EXIST.getValue(), auditInfoList);
					break;
				case PHONE:
					BlacklistPhone blacklistPhone = blacklistPhoneService.findById(idList.get(0));
					if (Utils.isNotNull(blacklistPhone)) {
						auditInfo.setName(blacklistService.findById(blacklistPhone.getBlacklistId()).getCompanyName());
						BusiUtils.updateAuditInfo(auditInfo, blacklistPhone.getBlacklistId(), null, null, "Phone [" + value + "]");
						auditInfoList.add(auditInfo);
						BusiUtils.updateAutoAuditRecord(autoAuditRecord, AutoAuditType.EXIST.getValue(), auditInfoList);
					}
					break;
				case CUSTNAME:
					BusiUtils.updateAuditInfo(auditInfo, null, value, null, "CustName [" + value + "]");
					auditInfoList.add(auditInfo);
					BusiUtils.updateAutoAuditRecord(autoAuditRecord, AutoAuditType.EXIST.getValue(), auditInfoList);
					break;
				}
			}
		}
		return autoAuditRecord;
	}

	/**
	 * 黑名单审核（先验证客户名称，然后是URL，最后是电话号码列表）
	 * 
	 * @param custName
	 *            客户名称
	 * @param url
	 *            URL
	 * @param phoneList
	 *            电话号码列表
	 * @return 自动验证结果
	 */
	private AutoAuditRecord auditBlacklist(String custName, String url, Set<String> phoneList) {
		if (Utils.isNull(custName)) {
			log.warn(LogEqualToNull("custName"));
			return null;
		}

		// 客户名称
		AutoAuditRecord autoAuditRecord = auditBlacklist(ValidType.CUSTNAME, custName);
		if (Utils.isNotNull(autoAuditRecord) && autoAuditRecord.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			return autoAuditRecord;
		}

		// URL
		if (StringUtils.isNotEmpty(url)) {
			autoAuditRecord = auditBlacklist(ValidType.URL, url);
			if (Utils.isNotNull(autoAuditRecord) && autoAuditRecord.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
				return autoAuditRecord;
			}
		}

		// 电话号码列表
		if (CollectionUtils.isNotEmpty(phoneList)) {
			for (String str : phoneList) {
				autoAuditRecord = auditBlacklist(ValidType.PHONE, str);
				if (Utils.isNotNull(autoAuditRecord) && autoAuditRecord.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
					return autoAuditRecord;
				}
			}
		}

		autoAuditRecord = new AutoAuditRecord();
		autoAuditRecord.setAutoAuditType(AutoAuditType.NEXT_STEP.getValue());
		return autoAuditRecord;
	}

	/**
	 * 季节性备案客户审核
	 * 
	 * @param custName
	 *            客户名称
	 * @param url
	 *            URL
	 * @param domain
	 *            主域
	 * @param phoneList
	 *            电话号码列表
	 * @param posIds
	 *            部门编号数组
	 * @return 自动验证结果
	 */
	private AutoAuditRecord auditSeasonFileCustlist(String custName, String url, String domain, Set<String> phoneList, Long[] posIds) {
		AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
		autoAuditRecord.setAutoAuditType(AutoAuditType.NEXT_STEP.getValue());

		List<Long> posIdList = Arrays.asList(posIds);
		List<SeasonCustList> seasonCustlistList = null;
		if (Utils.isNotNull(url)) {
			seasonCustlistList = seasonCustListService.equalUrl(url, posIdList);
			if (CollectionUtils.isNotEmpty(seasonCustlistList)) {
				SeasonCustList seasonCustList = seasonCustlistList.get(0);
				AuditInfo auditInfo = BusiUtils.newAuditInfo(seasonCustList.getCustId(), seasonCustList.getCustName(), AutoAuditSourceType.SEASON_FILE_CUST_LIST.getValue(), BusiUtils.getRemark(ValidType.URL, url));
				List<AuditInfo> auditInfoList = new ArrayList<AuditInfo>();
				auditInfoList.add(auditInfo);
				BusiUtils.updateAutoAuditRecord(autoAuditRecord, AutoAuditType.EXIST.getValue(), auditInfoList);
				return autoAuditRecord;
			}
		}

		// 客户名称（核心词）、URL（主域）和电话（精确），三者有一个相同，标记为“季节性备案”
		List<AuditInfo> auditInfoList = new ArrayList<AuditInfo>();

		// 客户名称
		if (StringUtils.isNotEmpty(custName)) {
			seasonCustlistList = seasonCustListService.querySeasonCustDataByCoreWord(custName, Arrays.asList(posIds), Constant.TINYSE_QUERY_LIMIT);
			seasonCustlistList = new ArrayList<SeasonCustList>();
			if (CollectionUtils.isNotEmpty(seasonCustlistList)) {
				for (SeasonCustList seasonCustList : seasonCustlistList) {
					AuditInfo auditInfo = BusiUtils.newAuditInfo(seasonCustList.getCustId(), seasonCustList.getCustName(), AutoAuditSourceType.SEASON_FILE_CUST_LIST.getValue(), BusiUtils.getRemark(ValidType.CUSTNAME, custName));
					auditInfoList.add(auditInfo);
				}
			}
		}

		// 主域
		if (StringUtils.isNotEmpty(domain)) {
			seasonCustlistList = seasonCustListService.querySeasonCustDataByDomain(domain, posIdList);
			if (CollectionUtils.isNotEmpty(seasonCustlistList)) {
				for (SeasonCustList seasonCustList : seasonCustlistList) {
					AuditInfo auditInfo = BusiUtils.newAuditInfo(seasonCustList.getCustId(), seasonCustList.getCustName(), AutoAuditSourceType.SEASON_FILE_CUST_LIST.getValue(), BusiUtils.getRemark(ValidType.DOMAIN, domain));
					auditInfoList.add(auditInfo);
				}
			}
		}

		// 电话
		if (CollectionUtils.isNotEmpty(phoneList)) {
			for (String phone : phoneList) {
				seasonCustlistList = seasonCustListService.querySeasonCustDataByPhone(phone, Arrays.asList(posIds));
				if (CollectionUtils.isNotEmpty(seasonCustlistList)) {
					for (SeasonCustList seasonCustList : seasonCustlistList) {
						AuditInfo auditInfo = BusiUtils.newAuditInfo(seasonCustList.getCustId(), seasonCustList.getCustName(), AutoAuditSourceType.SEASON_FILE_CUST_LIST.getValue(), BusiUtils.getRemark(ValidType.PHONE, phone));
						auditInfoList.add(auditInfo);
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(auditInfoList)) {
			autoAuditRecord.setCustomer(auditInfoList);
			autoAuditRecord.setAutoAuditType(AutoAuditType.SEASON_FILE.getValue());
		}

		return autoAuditRecord;
	}

	/**
	 * 日志格式化
	 * 
	 * @param name
	 *            参数名称
	 * @param operator
	 *            操作名称
	 * @param result
	 *            结果值
	 */
	private String LogFormater(String name, String operator, String result) {
		return String.format("%s %s %s", name, operator, result);
	}

	/**
	 * 输出日志：“参数为null”
	 * 
	 * @param name
	 *            参数名称
	 */
	private String LogEqualToNull(String name) {
		return LogFormater(name, "=", "null");
	}

	/**
	 * 输出日志：“参数小于等于0”
	 * 
	 * @param name
	 *            参数名称
	 */
	private String LogLessEqualThanZero(String name) {
		return LogFormater(name, "<=", "0");
	}

	private String LogEqualToZero(String name) {
		return LogFormater(name, "=", "0");
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 老客户白名单审核 *********************************************************************
	 * 
	 * @param url
	 *            公司URL
	 * @param ucid
	 *            当前处理者的id
	 * @return 0：pass 1: continue audit
	 */
	// TODO Anders Zhu : 函数需要重构
	private AutoAuditRecord auditShifenCustWhitelist(String url, Long ucid, Long custid) {
		// 在老客户白名单表中old_cust_whitelist
		// URL精确匹配并且当前为指定的销售人员
		// 存在 pass
		if (Utils.isNull(url)) {
			log.warn(LogEqualToNull("url"));
			return null;
		} else if (Utils.isEqualToZero(url.length())) {
			log.warn(LogEqualToZero("url"));
			return null;
		}

		AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
		autoAuditRecord.setAutoAuditType(AutoAuditType.NEXT_STEP.getValue());

		ShifenCustWhiteList shifenCustWhiteList = shifenCustWhiteListService.equalUrl(url);
		if (Utils.isNotNull(shifenCustWhiteList) && shifenCustWhiteList.getUserId().equals(ucid)) {
			List<Map<String, Object>> list = auditService.listMatchCustUrl(url);
			// 得到盘古中的客户资料
			List<Map<String, Object>> panguList = custService.findBySiteUrl(url);
			Set<Long> idSet = new HashSet<Long>();
			// TODO Anders Zhu : 以下两个方法重复，考虑重构
			if (CollectionUtils.isNotEmpty(list)) {
				for (Map<String, Object> map : list) {
					// TODO Anders Zhu : 重构
					idSet.add((Long) map.get("cust_id"));
				}
			}
			if (CollectionUtils.isNotEmpty(panguList)) {
				for (Map<String, Object> map : panguList) {
					// TODO Anders Zhu : 重构
					idSet.add((Long) map.get("cust_id"));
				}
			}

			// 去除客户本身
			if (Utils.isNotNull(custid)) {
				idSet.remove(custid);
			}

			if (Utils.isGreaterThanZero(idSet.size())) {
				// 重复
				List<AuditInfo> auditInfoList = autoAuditRecord.getCustomer();
				if (auditInfoList == null)
					auditInfoList = new ArrayList<AuditInfo>();
				for (Map<String, Object> map : list) {
					// 去除客户本身
					if (custid != null && custid.equals(map.get("cust_id"))) {
						continue;
					}
					AuditInfo auditInfo = new AuditInfo();
					auditInfo.setId((Long) map.get("cust_id"));
					auditInfo.setName((String) map.get("cust_full_name"));
					auditInfo.setAutoAuditSource(AutoAuditSourceType.SHIFEN_CUST_WHITELIST.getValue());

					auditInfo.setRemark("URL [" + url + "]");
					auditInfoList.add(auditInfo);
				}
				for (Map<String, Object> map : panguList) {
					// 去除客户本身
					if (custid != null && custid.equals(map.get("custId"))) {
						continue;
					}
					AuditInfo auditInfo = new AuditInfo();
					auditInfo.setId((Long) map.get("custId"));
					auditInfo.setName((String) map.get("fullName"));
					auditInfo.setAutoAuditSource(AutoAuditSourceType.SHIFEN_CUST_WHITELIST.getValue());
					auditInfo.setRemark("URL [" + url + "]");
					auditInfoList.add(auditInfo);
				}
				autoAuditRecord.setAutoAuditType(AutoAuditType.EXIST.getValue());
				autoAuditRecord.setCustomer(auditInfoList);
			} else {
				// 通过
				autoAuditRecord.setAutoAuditType(AutoAuditType.PASS.getValue());
			}
		}

		return autoAuditRecord;

	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * URL审核 *********************************************************************
	 * 
	 * @param url
	 *            公司URL
	 */
	// TODO Anders Zhu : 重构该函数,巨大的方法
	private AutoAuditRecord auditURL(String url, String domain, Long custid, Long[] posids, Long inUserId) throws AutoAuditException {
		if (url == null || domain == null) {
			log.info("In function auditURL url==null||domain==null");
			return null;
		}
		AutoAuditRecord result = new AutoAuditRecord();
		result.setAutoAuditType(AutoAuditType.NEXT_STEP.getValue());

		boolean exist = urlWhitelistService.isDomainAndPosIdsExist(domain, posids);
		// 如果主域在白名单中
		if (exist) {
			List<Map<String, Object>> saleList = auditService.listMatchCustUrl(url);
			Set<Long> slids = new HashSet<Long>();
			if (saleList != null && saleList.size() > 0) {
				for (Map<String, Object> mp : saleList) {
					slids.add((Long) mp.get("cust_id"));
				}
			}
			// 去除客户本身
			if (custid != null) {
				slids.remove(custid);
			}

			if (slids.size() > 0) {
				List<AuditInfo> auditInfos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : saleList) {
					Long cust_id = (Long) mp.get("cust_id");
					if (custid == null || !custid.equals(cust_id)) {
						AuditInfo auditInfo = new AuditInfo();
						auditInfo.setId(cust_id);
						auditInfo.setName((String) mp.get("cust_full_name"));
						Long follower = getFollowerId(cust_id);
						if (follower == null || follower.intValue() == 0) {
							auditInfo.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
						} else {
							auditInfo.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());
						}
						auditInfo.setRemark("URL [" + url + "]");

						auditInfos.add(auditInfo);
					}
				}
				result.setCustomer(auditInfos);
				result.setAutoAuditType(AutoAuditType.EXIST.getValue());
				return result;
			}

			// shifen
			List<Map<String, Object>> sflist = shifenCustomerService.equalSiteUrl(url);
			if (sflist != null && sflist.size() > 0) {
				// throw new AutoAuditException("与客户资料重复");
				List<AuditInfo> auditInfos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : sflist) {
					Long cust_id = (Long) mp.get("customerd");
					// if (custid == null || !custid.equals(cust_id)) {
					AuditInfo auditInfo = new AuditInfo();
					auditInfo.setId(cust_id);
					String name = (String) mp.get("realcompanyname");
					if (StringUtils.isEmpty(name))
						name = (String) mp.get("companyname");
					if (StringUtils.isEmpty(name))
						name = (String) mp.get("customername");
					auditInfo.setName(name);

					auditInfo.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());
					auditInfo.setRemark("URL [" + url + "]");

					auditInfos.add(auditInfo);
					// }
				}

				result.setCustomer(auditInfos);
				result.setAutoAuditType(AutoAuditType.EXIST.getValue());
				return result;
			}

		} else {
			// url 主域不在运营单位的白名单
			List<Map<String, Object>> salelist = auditService.listMatchDomain(domain);

			Set<Long> slids = new HashSet<Long>();
			if (salelist != null && salelist.size() > 0) {
				for (Map<String, Object> mp : salelist) {
					slids.add((Long) mp.get("cust_id"));
				}
			}

			// 去除客户本身
			if (custid != null) {
				slids.remove(custid);
			}

			if (slids.size() > 0) {
				List<AuditInfo> auditInfos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : salelist) {
					Long cust_id = (Long) mp.get("cust_id");
					if (custid == null || !custid.equals(cust_id)) {
						AuditInfo auditInfo = new AuditInfo();
						auditInfo.setId(cust_id);
						auditInfo.setName((String) mp.get("cust_full_name"));
						Long follower = getFollowerId(cust_id);
						if (follower == null || follower.intValue() == 0) {
							auditInfo.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
						} else {
							auditInfo.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());
						}
						auditInfo.setRemark("Domain [" + domain + "]");

						auditInfos.add(auditInfo);
					}
				}
				result.setCustomer(auditInfos);
				result.setAutoAuditType(AutoAuditType.EXIST.getValue());
				return result;
			}

			// shifen
			List<Map<String, Object>> sflist = shifenCustomerService.equalUrlDomain(domain);
			if (sflist != null && sflist.size() > 0) {
				List<AuditInfo> auditInfos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : sflist) {
					Long cust_id = (Long) mp.get("customerd");
					// if (custid == null || !custid.equals(cust_id)) {
					AuditInfo auditInfo = new AuditInfo();

					auditInfo.setId(cust_id);
					String name = (String) mp.get("realcompanyname");
					if (StringUtils.isEmpty(name))
						name = (String) mp.get("companyname");
					if (StringUtils.isEmpty(name))
						name = (String) mp.get("customername");
					auditInfo.setName(name);

					auditInfo.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());
					auditInfo.setRemark("Domain [" + domain + "]");

					auditInfos.add(auditInfo);
					// }
				}

				result.setCustomer(auditInfos);
				result.setAutoAuditType(AutoAuditType.EXIST.getValue());
				return result;
			}

			/*******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
			 * 主域前缀匹配
			 ******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
			// TODO Anders Zhu : 理解+重构
			String predomain = URLUtils.getPreDomain(url);
			List<AuditInfo> infos = new ArrayList<AuditInfo>();
			// sale
			salelist = auditService.listPreMatchDomain(predomain);

			slids = new HashSet<Long>();
			if (salelist != null && salelist.size() > 0) {
				for (Map<String, Object> mp : salelist) {
					Long id = ((Number) mp.get("cust_id")).longValue();

					// 去除客户本身
					if (custid != null && custid.equals(id)) {
						continue;
					}
					AuditInfo ai = new AuditInfo();
					ai.setId(id);
					ai.setName((String) mp.get("cust_full_name"));
					Long follower = getFollowerId(id);
					if (follower == null || follower.intValue() == 0) {
						ai.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
					} else {
						ai.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());
					}
					ai.setRemark("PreDomain [" + predomain + "]");
					infos.add(ai);
				}
			}

			// shifen
			sflist = shifenCustomerService.containUrlDomain(domain);
			if (sflist != null && sflist.size() > 0) {
				for (Map<String, Object> mp : sflist) {
					Long id = (Long) mp.get("customerd");

					// 去除客户本身
					// if (custid != null && custid.equals(id)) {
					// continue;
					// }
					AuditInfo ai = new AuditInfo();
					ai.setId((Long) mp.get("customerd"));

					// 公司真实名称 > 公司名称 > 帐号
					String name = (String) mp.get("realcompanyname");
					if (StringUtils.isEmpty(name)) {
						name = (String) mp.get("companyname");
					}
					if (StringUtils.isEmpty(name)) {
						name = (String) mp.get("customername");
					}
					ai.setName(name);

					ai.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());
					ai.setRemark("PreDomain [" + domain + "]");
					infos.add(ai);
				}
			}

			if (infos.size() > 0) {
				// 质疑
				result.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
				result.setCustomer(infos);
			}

		}
		return result;

	}

	private Long getFollowerId(Long custId) {
		Map<String, Object> map = cachedCustMap.get().get(custId);
		if (Utils.isNotNull(map))
			return (Long) map.get("followId");

		Cust cust = custService.findById(custId);
		Long followerId = null;
		if (Utils.isNull(cust)) {
			followerId = followAssignService.getFollowerId(custId);
		} else {
			followerId = cust.getInUcid();
			Map<String, Object> propMap = new HashMap<String, Object>();
			propMap.put(FieldConstant.CUSTID, cust.getId());
			propMap.put(FieldConstant.ISSIGNED, cust.getStat1().equals(Constant.PANGU_STAT1_CONTRACT_07));
			propMap.put(FieldConstant.FOLLOWID, cust.getInUcid());
			propMap.put(FieldConstant.FULLNAME, cust.getFullName());
			cachedCustMap.get().put(cust.getId(), propMap);
		}
		return followerId;
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 在shifen表中精确匹配公司名，范围为业务部门上生效的 *********************************************************************
	 * 
	 * @param custName
	 *            公司名
	 * @return 匹配的公司信息
	 */
	// TODO Anders Zhu : 重构+理解
	private List<ShifenData> exactMatchCustNameOldCust(String custName) {
		List<ShifenData> sflist = new ArrayList<ShifenData>();
		if (custName == null || custName.length() == 0) {
			return null;
		}
		List<Long> sfIdList = shifenCustomerService.equalCompanyName(custName);
		List<ShifenCustomer> sfCustList = shifenCustomerService.getShifenCustomerByCustIdList(sfIdList);
		if (sfCustList != null) {
			for (ShifenCustomer shifenCustomer : sfCustList) {
				ShifenData sfData = new ShifenData();
				sfData.setRealcompanyname(shifenCustomer.getRealcompanyname());
				sfData.setCompanyname(shifenCustomer.getCompanyname());
				sfData.setCustomername(shifenCustomer.getCustomername());
				sfData.setId(shifenCustomer.getCustomerd());
				sfData.setPhone(shifenCustomer.getConphone());

				sflist.add(sfData);
			}
		}
		return sflist;
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 在sale表中精确匹配公司名 *********************************************************************
	 * 
	 * @param custName
	 *            公司名
	 * @return 匹配的公司信息
	 */
	// TODO Anders Zhu : 重构+理解
	private List<SaleData> exactMatchCustNameSale(String custName) {
		List<SaleData> salelist = new ArrayList<SaleData>();
		if (custName == null || custName.length() == 0) {
			return null;
		}
		List<Long> saleIdList = customerService.equalCustFullName(custName);
		saleIdList.addAll(custService.findByFullName(custName));
		if (saleIdList != null) {
			for (Long saleId : saleIdList) {
				SaleData saleData = new SaleData();
				saleData.setCompanyname(custName);
				saleData.setId(saleId);

				salelist.add(saleData);
			}
		}

		return salelist;
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 在sale表中精确匹配广告代理公司分支公司名 *********************************************************************
	 * 
	 * @param custBranchName
	 *            分支公司名
	 * @return 匹配的公司信息
	 */
	// TODO Anders Zhu : 重构+理解
	private List<SaleData> exactMatchCustBranchNameSale(String custBranchName, CustType custType) {
		List<SaleData> salelist = new ArrayList<SaleData>();
		if (custBranchName == null || custBranchName.length() == 0) {
			return null;
		}
		List<Long> saleIdList = customerService.equalCustBranchNameOrCustName(custBranchName, custType);
		saleIdList.addAll(custService.findByNameOrBranch(custBranchName, custType));
		if (saleIdList != null) {
			for (Long saleId : saleIdList) {
				SaleData saleData = new SaleData();
				saleData.setCompanyname(custBranchName);
				saleData.setId(saleId);

				salelist.add(saleData);
			}
		}
		return salelist;
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 老客户审核 *********************************************************************
	 * 
	 * @param url
	 *            公司URL
	 * @param ucid
	 *            当前处理者的id
	 * @return 0：pass 1: continue audit
	 */
	private List<AutoAuditRecord> auditOldCust(String custFullName, String custName, Short custType, Set<String> phoneList) {

		List<AutoAuditRecord> result = new LinkedList<AutoAuditRecord>();
		// 企业
		if (custType.intValue() == CustType.GENERAL_ENTERPRISE.getValue() || custType.intValue() == CustType.ADVERTISING_AGENCY.getValue()) {

			if (custFullName == null) {
				log.info("In function auditOldCust custFullName==null");
				return null;
			}
			List<ShifenData> sflist = tinyseMgr.queryShifenDataWithHold(custFullName, Constant.TINYSE_QUERY_LIMIT);
			sflist = filterShifenDataList(sflist, custFullName);
			// 在数据库中精确匹配custFullName
			List<ShifenData> sflistDB = exactMatchCustNameOldCust(custFullName);
			if (sflistDB != null && sflistDB.size() > 0) {
				sflist.addAll(sflistDB);
			}

			if (sflist != null && sflist.size() > 0) {

				for (ShifenData sfdata : sflist) {
					AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
					autoAuditRecord.setAutoAuditType(AutoAuditType.OLD_TO_NEW.getValue());

					List<AuditInfo> infos = new ArrayList<AuditInfo>();
					AuditInfo ai = new AuditInfo();

					// ID
					ai.setId(sfdata.getId());
					// 公司真实名称 > 公司名称 > 帐号
					String name = sfdata.getRealcompanyname();
					if (StringUtils.isEmpty(name)) {
						name = sfdata.getCompanyname();
					}
					if (StringUtils.isEmpty(name)) {
						name = sfdata.getCustomername();
					}
					ai.setName(name);
					ai.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());
					ai.setRemark("CustName [" + custFullName + "]");
					infos.add(ai);
					autoAuditRecord.setCustomer(infos);

					result.add(autoAuditRecord);
				}
				return result;
			}
		}

		// 个人
		else if (custType.intValue() == CustType.PERSONAL_CUSTOMER.getValue()) {
			if (custFullName == null) {
				log.info("In function auditOldCust custFullName==null");
				return null;
			}
			List<ShifenData> sflist = tinyseMgr.queryShifenDataWithHold(custFullName, Constant.TINYSE_QUERY_LIMIT);
			sflist = filterShifenDataList(sflist, custFullName);
			// 在数据库中精确匹配custFullName
			List<ShifenData> sflistDB = exactMatchCustNameOldCust(custFullName);
			if (sflistDB != null && sflistDB.size() > 0) {
				sflist.addAll(sflistDB);
			}

			if (sflist != null && sflist.size() > 0) {
				for (ShifenData sfdata : sflist) {
					// 检查客户电话
					if (phoneList != null) {
						for (String ph : phoneList) {
							List<ShifenData> phlist = tinyseMgr.queryShifenDataWithWordPhrase(ph, Constant.TINYSE_QUERY_LIMIT);

							// 匹配到电话号码
							if (phlist != null && phlist.size() > 0) {

								AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
								autoAuditRecord.setAutoAuditType(AutoAuditType.OLD_TO_NEW.getValue());

								List<AuditInfo> infos = new ArrayList<AuditInfo>();
								AuditInfo ai = new AuditInfo();

								// ID
								ai.setId(sfdata.getId());
								// 公司真实名称 > 公司名称 > 帐号
								String name = sfdata.getRealcompanyname();
								if (StringUtils.isEmpty(name)) {
									name = sfdata.getCompanyname();
								}
								if (StringUtils.isEmpty(name)) {
									name = sfdata.getCustomername();
								}
								ai.setName(name);
								ai.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());
								ai.setRemark("CustName [" + custFullName + "]" + "Phone [" + ph + "]");
								infos.add(ai);
								autoAuditRecord.setCustomer(infos);

								result.add(autoAuditRecord);
								// return result;
							}
						}
					}
				}
				return result;
			}

		} else if (custType.intValue() == CustType.SPECIAL_ENTERPRISE.getValue()) {
			if (custFullName == null) {
				log.info("In function auditOldCust custFullName==null");
				return null;
			}
			// if(custName==null)
			// {
			// log.info("In function auditOldCust custFullName==null");
			// return null;
			// }
			// 在老客户名单中查询
			AutoAuditRecord ret = auditShifenCustSpecial(custFullName);
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.OLD_TO_NEW.getValue()) {
				// 老户新开
				result.add(ret);
			}

			if (custName != null && !custName.equals(custFullName)) {
				// 在老客户名单中查询
				ret = auditShifenCustSpecial(custName);
				if (ret != null && ret.getAutoAuditType() == AutoAuditType.OLD_TO_NEW.getValue()) {
					// 老户新开
					result.add(ret);
				}
			}
			return result;

		}
		// 非法
		else {
			log.info("In function auditOldCust custType is Wrong:" + custType);
			return null;
		}

		return result;

	}

	private List<ShifenData> filterShifenDataList(List<ShifenData> sfdl, String custFullName) {
		if (sfdl != null) {
			log.info("before filterShifenDataList,list size=" + sfdl.size());
		} else {
			log.info("before filterShifenDataList,list size=0");
		}
		List<ShifenData> ret = new ArrayList<ShifenData>();
		custFullName = tinyseMgr.removeNoneCoreWord(custFullName);
		if (sfdl != null) {
			for (ShifenData sfd : sfdl) {
				String name = tinyseMgr.removeNoneCoreWord(sfd.getCompanyname());
				if (custFullName != null && name != null && custFullName.replaceAll(companyComparePattern, "").length() == name.replaceAll(companyComparePattern, "").length()) {
					ret.add(sfd);
					continue;
				}
				name = tinyseMgr.removeNoneCoreWord(sfd.getRealcompanyname());
				if (custFullName != null && name != null && custFullName.replaceAll(companyComparePattern, "").length() == name.replaceAll(companyComparePattern, "").length()) {
					ret.add(sfd);
					continue;
				}
				name = tinyseMgr.removeNoneCoreWord(sfd.getCustomername());
				if (custFullName != null && name != null && custFullName.replaceAll(companyComparePattern, "").length() == name.replaceAll(companyComparePattern, "").length()) {
					ret.add(sfd);
					continue;
				}
			}
		}
		if (ret != null) {
			log.info("after filterShifenDataList,list size=" + ret.size());
		} else {
			log.info("after filterShifenDataList,list size=0");
		}
		return ret;
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 在老客户中进行特殊企业类型的客户审核 *********************************************************************
	 * 
	 * @param custName
	 *            公司名
	 * @return 查询到的老户新开信息
	 */
	private AutoAuditRecord auditShifenCustSpecial(String custName) {

		if (custName == null || custName.length() == 0) {
			log.info("In function auditOldCustSpecial custName==null||custName.length()==0");
			return null;
		}
		AutoAuditRecord result = new AutoAuditRecord();
		result.setAutoAuditType(AutoAuditType.NEXT_STEP.getValue());
		List<ShifenData> sflist = tinyseMgr.queryShifenDataWithHold(custName, Constant.TINYSE_QUERY_LIMIT);
		sflist = filterShifenDataList(sflist, custName);
		// 在数据库中精确匹配custName
		List<ShifenData> sfListDB = exactMatchCustNameOldCust(custName);
		if (sfListDB != null && sfListDB.size() > 0) {
			sflist.addAll(sfListDB);
		}

		if (sflist != null && sflist.size() > 0) {

			List<AuditInfo> infos = new ArrayList<AuditInfo>();
			for (ShifenData sfdata : sflist) {

				AuditInfo ai = new AuditInfo();
				// ID
				ai.setId(sfdata.getId());
				// 公司真实名称 > 公司名称 > 帐号
				String name = sfdata.getRealcompanyname();
				if (StringUtils.isEmpty(name)) {
					name = sfdata.getCompanyname();
				}
				if (StringUtils.isEmpty(name)) {
					name = sfdata.getCustomername();
				}
				ai.setName(name);
				ai.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());
				ai.setRemark("CustName [" + custName + "]");
				infos.add(ai);

			}
			result.setAutoAuditType(AutoAuditType.OLD_TO_NEW.getValue());
			result.setCustomer(infos);
		}
		return result;
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 自由库、公共库审核 *********************************************************************
	 * 
	 * @param url
	 *            公司URL
	 * @param ucid
	 *            当前处理者的id url不为空: 自有库判断：  普通企业类型的客户：如公司名称与自有库相同则判断录入人与自有库拥有人是否一致，一致则视为一户多开，否则视为重复；  个人类型的客户：如公司名称与自有库相同则进一步检查联系电话，如电话与自有库相同则视为质疑；  个人类型客户的联系电话检查范围不限于同名的资料，是在所有自有库范围内；  特殊企业类型的客户：第一个框的公司名称如与自有库相同则视为质疑；两个框组合的公司名称如与自有库相同则也视为质疑；  代理广告公司的客户：第二个框的公司名称如与自有库相同则视为质疑。  公共库判断：  普通企业类型的客户：如公司名称与公共库相同则视为质疑；  个人类型的客户：如公司名称与公共库相同则进一步检查联系电话，如电话与公共库相同则视为质疑；  个人类型客户的联系电话检查范围不限于同名的资料，是在所有公共库范围内；  特殊企业类型的客户：第一个框的公司名称如与公共库有库相同则视为质疑；两个框组合的公司名称如与公共库库相同则也视为质疑；  代理广告公司的客户：第二个框的公司名称如与公共库相同则视为质疑。 url为空:  自有库判断：  普通企业类型的客户：如公司名称与自有库相同则判断录入人与自有库拥有人是否一致，一致则视为一户多开，否则视为重复；  个人类型的客户：如公司名称与自有库相同则进一步检查联系电话，如电话与自有库相同则视为质疑；  个人类型客户的联系电话检查范围不限于同名的资料，是在所有自有库范围内；  特殊企业类型的客户：第一个框的公司名称如与自有库相同则视为质疑；两个框组合的公司名称如与自有库相同则也视为质疑；  代理广告公司的客户：第二个框的公司名称如与自有库相同，则视为质疑。  公共库判断：  普通企业类型的客户：如公司名称与公共库相同则视为重复； 
	 *            个人类型的客户：如公司名称与公共库相同则进一步检查联系电话，如电话与公共库相同则视为质疑；  个人类型客户的联系电话检查范围不限于同名的资料，是在所有公共库范围内；  特殊企业类型的客户：第一个框的公司名称如与公共库相同则视为质疑；两个框组合的公司名称如与公共库相同则也视为质疑；  代理广告公司的客户：第二个框的公司名称如与公共库相同则视为重复。
	 */
	private List<AutoAuditRecord> auditSaleCust(Long custid, Long ucid, String custFullName, String custName, Short custType, Set<String> phoneList, String url, Long posid) throws AutoAuditException {
		List<AutoAuditRecord> result = new LinkedList<AutoAuditRecord>();
		if (custFullName == null) {
			log.info("In function auditSaleCust custFullName==null");
			return null;
		}
		List<SaleData> salelist = tinyseMgr.querySaleDataWithHold(custFullName, Constant.TINYSE_QUERY_LIMIT);
		salelist = filterSaleDataList(salelist, custFullName);
		// 在数据库中精确匹配custFullName
		if (custType.intValue() != CustType.ADVERTISING_AGENCY.getValue()) {
			List<SaleData> saleListDB = exactMatchCustNameSale(custName);
			BusiUtils.mergeSaleData(salelist, saleListDB);
		} else {
			List<SaleData> saleListDB = exactMatchCustBranchNameSale(custName, CustType.ADVERTISING_AGENCY);
			BusiUtils.mergeSaleData(salelist, saleListDB);
		}

		// 特殊企业类型的客户：第一个框的公司名称,和两个框组合的公司名称
		if (custType.intValue() == CustType.SPECIAL_ENTERPRISE.getValue()) {
			if (!custName.equals(custFullName)) {
				List<SaleData> tempSalelist = tinyseMgr.querySaleDataWithHold(custName, Constant.TINYSE_QUERY_LIMIT);
				tempSalelist = filterSaleDataList(tempSalelist, custName);
				List<SaleData> tempSaleListDB = exactMatchCustNameSale(custName);
				List<SaleData> tempSaleListDBBranch = exactMatchCustBranchNameSale(custName, CustType.SPECIAL_ENTERPRISE);
				BusiUtils.mergeSaleData(salelist, tempSalelist);
				BusiUtils.mergeSaleData(salelist, tempSaleListDB);
				BusiUtils.mergeSaleData(salelist, tempSaleListDBBranch);
			}
		}
		// 个人类型客户，不去掉地域词
		List<SaleData> allSaleList = null;
		if (custType.intValue() != CustType.PERSONAL_CUSTOMER.getValue()) {
			String noAreaWord = tinyseMgr.removeNoneCoreWord(custFullName);
			noAreaWord = tinyseMgr.removeAreaWord(noAreaWord, posid);
			allSaleList = tinyseMgr.querySaleDataWithHold(noAreaWord, Constant.TINYSE_QUERY_LIMIT);
			allSaleList = filterSaleDataAreaWordList(allSaleList, custFullName, posid);
		}

		if (salelist != null && salelist.size() > 0) {
			// 将设置缓存
			List<Long> saleIds = new ArrayList<Long>();
			for (SaleData saledata : salelist) {
				saleIds.add(saledata.getId());
			}
			List<Map<String, Object>> custList = custService.findCustIdStatInUcidFullNameByCustIdList(saleIds);
			for (Map<String, Object> map : custList) {
				Map<String, Object> propMap = new HashMap<String, Object>();
				propMap.put("custId", map.get(FieldConstant.ID));
				propMap.put("isSigned", map.get(FieldConstant.STAT_1).equals(Constant.PANGU_STAT1_CONTRACT_07));
				propMap.put("followId", map.get(FieldConstant.IN_UCID));
				propMap.put("fullName", map.get(FieldConstant.FULL_NAME));
				cachedCustMap.get().put((Long) map.get(FieldConstant.ID), propMap);
			}
			for (SaleData saledata : salelist) {
				if (custid != null && custid.equals(saledata.getId())) {
					continue;
				}
				if (isSaleAlreadySign(saledata.getId())) {
					continue;
				}
				if (url != null && url.trim().length() > 0) {
					Long follower = getFollowerId(saledata.getId());
					boolean isPublic = (follower == null || follower.intValue() == 0);
					if (true)//
					{
						if (custType.intValue() == CustType.GENERAL_ENTERPRISE.getValue()) {
							// 自有库判断
							// 普通企业类型的客户：如公司名称与自有库相同则判断录入人与自有库拥有人是否一致，一致则视为一户多开，否则视为重复；
							if (!isPublic) {
								AuditInfo ai = new AuditInfo();
								ai.setId(saledata.getId());
								ai.setName(saledata.getCompanyname());
								ai.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());
								ai.setRemark("CustName [" + custFullName + "]");
								List<AuditInfo> infos = new ArrayList<AuditInfo>();
								infos.add(ai);
								// 录入人与自有库拥有人一致,一户多开
								if (follower.equals(ucid)) {
									AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
									autoAuditRecord.setAutoAuditType(AutoAuditType.ONE_TO_MANY.getValue());
									autoAuditRecord.setCustomer(infos);
									result.add(autoAuditRecord);
								}
								// 重复
								else {
									AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
									autoAuditRecord.setCustomer(infos);
									autoAuditRecord.setAutoAuditType(AutoAuditType.EXIST.getValue());
									result.clear();
									result.add(autoAuditRecord);
									return result;
								}
							}
							// 公共库判断
							// 普通企业类型的客户：如公司名称与公共库相同则视为质疑； 
							else {
								AuditInfo ai = new AuditInfo();
								ai.setId(saledata.getId());
								ai.setName(saledata.getCompanyname());
								ai.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
								ai.setRemark("CustName [" + custFullName + "]");
								List<AuditInfo> infos = new ArrayList<AuditInfo>();
								infos.add(ai);
								AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
								autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
								autoAuditRecord.setCustomer(infos);
								result.add(autoAuditRecord);
							}
						}
						// 个人类型的客户：如公司名称与自有库相同则进一步检查联系电话，如电话与自有库相同则视为质疑；
						else if (custType.intValue() == CustType.PERSONAL_CUSTOMER.getValue()) {
							// 客户电话
							if (phoneList != null) {
								for (String phone : phoneList) {
									// List<Long> ids = phoneAuditService.listMatchPhoneContact(phone);
									List<Long> ids = new ArrayList<Long>();

									if (ids == null || ids.size() == 0) {
										continue;
									}
									Set<Long> cids = new HashSet<Long>(ids);
									// 删除客户本身
									if (custid != null) {
										cids.remove(custid);
									}
									// 未匹配到客户
									if (cids == null || cids.size() == 0) {
										continue;
									}
									AuditInfo ai = new AuditInfo();
									ai.setId(saledata.getId());
									ai.setName(saledata.getCompanyname());
									ai.setAutoAuditSource(isPublic ? AutoAuditSourceType.PUBLIC_LIB.getValue() : AutoAuditSourceType.PERSONAL_LIB.getValue());
									ai.setRemark("CustName [" + custFullName + "]" + "Phone [" + phone + "]");
									List<AuditInfo> infos = new ArrayList<AuditInfo>();
									infos.add(ai);
									AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
									autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
									autoAuditRecord.setCustomer(infos);
									result.add(autoAuditRecord);
								}
							}
						}
						// 特殊企业类型的客户 或 代理 质疑
						else if (custType.intValue() == CustType.SPECIAL_ENTERPRISE.getValue() || custType.intValue() == CustType.ADVERTISING_AGENCY.getValue()) {

							AuditInfo ai = new AuditInfo();
							ai.setId(saledata.getId());
							ai.setName(saledata.getCompanyname());
							ai.setAutoAuditSource(isPublic ? AutoAuditSourceType.PUBLIC_LIB.getValue() : AutoAuditSourceType.PERSONAL_LIB.getValue());
							ai.setRemark("CustName [" + custFullName + "]");
							List<AuditInfo> infos = new ArrayList<AuditInfo>();
							infos.add(ai);
							AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
							autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
							autoAuditRecord.setCustomer(infos);
							result.add(autoAuditRecord);
						}
					}
				}
				// 如果url为空
				else {
					// 普通企业类型的客户：如公司名称与自有库相同则判断录入人与自有库拥有人是否一致，一致则视为一户多开，否则视为重复；
					Long follower = getFollowerId(saledata.getId());
					boolean isPublic = (follower == null || follower.intValue() == 0);
					if (custType.intValue() == CustType.GENERAL_ENTERPRISE.getValue()) {
						// 自有库判断
						// 普通企业类型的客户：如公司名称与自有库相同则判断录入人与自有库拥有人是否一致，一致则视为一户多开，否则视为重复；
						if (!isPublic) {
							AuditInfo ai = new AuditInfo();
							ai.setId(saledata.getId());
							ai.setName(saledata.getCompanyname());
							ai.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());
							ai.setRemark("CustName [" + custFullName + "]");
							List<AuditInfo> infos = new ArrayList<AuditInfo>();
							infos.add(ai);
							// 录入人与自有库拥有人一致,一户多开
							if (follower.equals(ucid)) {
								AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
								autoAuditRecord.setAutoAuditType(AutoAuditType.ONE_TO_MANY.getValue());
								autoAuditRecord.setCustomer(infos);
								result.add(autoAuditRecord);
							}
							// 重复
							else {
								AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
								autoAuditRecord.setCustomer(infos);
								autoAuditRecord.setAutoAuditType(AutoAuditType.EXIST.getValue());
								result.clear();
								result.add(autoAuditRecord);
								return result;
							}
						}
						// 公共库判断
						// 普通企业类型的客户：如公司名称与公共库相同则视为重复；  
						else {
							AuditInfo ai = new AuditInfo();
							ai.setId(saledata.getId());
							ai.setName(saledata.getCompanyname());
							ai.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
							ai.setRemark("CustName [" + custFullName + "]");
							List<AuditInfo> infos = new ArrayList<AuditInfo>();
							infos.add(ai);
							AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
							autoAuditRecord.setAutoAuditType(AutoAuditType.EXIST.getValue());
							autoAuditRecord.setCustomer(infos);
							result.clear();
							result.add(autoAuditRecord);
							return result;
						}
					}
					// 个人类型的客户：如公司名称与自有库相同则进一步检查联系电话，如电话与自有库相同则视为质疑；
					else if (custType.intValue() == CustType.PERSONAL_CUSTOMER.getValue()) {
						// 客户电话
						if (phoneList != null) {
							for (String phone : phoneList) {
								// List<Long> ids = phoneAuditService.listMatchPhoneContact(phone);
								List<Long> ids = new ArrayList<Long>();

								if (ids == null || ids.size() == 0) {
									continue;
								}
								Set<Long> cids = new HashSet<Long>(ids);
								// 删除客户本身
								if (custid != null) {
									cids.remove(custid);
								}
								// 未匹配到客户
								if (cids == null || cids.size() == 0) {
									continue;
								}
								AuditInfo ai = new AuditInfo();
								ai.setId(saledata.getId());
								ai.setName(saledata.getCompanyname());
								ai.setAutoAuditSource(isPublic ? AutoAuditSourceType.PERSONAL_LIB.getValue() : AutoAuditSourceType.PERSONAL_LIB.getValue());
								ai.setRemark("CustName [" + custFullName + "]" + "Phone [" + phone + "]");
								List<AuditInfo> infos = new ArrayList<AuditInfo>();
								infos.add(ai);
								AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
								autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
								autoAuditRecord.setCustomer(infos);
								result.add(autoAuditRecord);
							}
						}
					}
					// 特殊企业类型的客户：第一个框的公司名称如与自有库相同则视为质疑；两个框组合的公司名称如与自有库相同则也视为质疑；
					else if (custType.intValue() == CustType.SPECIAL_ENTERPRISE.getValue()) {

						AuditInfo ai = new AuditInfo();
						ai.setId(saledata.getId());
						ai.setName(saledata.getCompanyname());
						ai.setAutoAuditSource(isPublic ? AutoAuditSourceType.PERSONAL_LIB.getValue() : AutoAuditSourceType.PERSONAL_LIB.getValue());
						ai.setRemark("CustName [" + custFullName + "]");
						List<AuditInfo> infos = new ArrayList<AuditInfo>();
						infos.add(ai);
						AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
						autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
						autoAuditRecord.setCustomer(infos);
						result.add(autoAuditRecord);
						// 代理广告公司的客户：第二个框的公司名称如与自有库相同，则视为质疑,与公共库相同视为重复
					} else if (custType.intValue() == CustType.ADVERTISING_AGENCY.getValue()) {
						AuditInfo ai = new AuditInfo();
						ai.setId(saledata.getId());
						ai.setName(saledata.getCompanyname());
						ai.setAutoAuditSource(isPublic ? AutoAuditSourceType.PERSONAL_LIB.getValue() : AutoAuditSourceType.PERSONAL_LIB.getValue());
						ai.setRemark("CustName [" + custFullName + "]");
						List<AuditInfo> infos = new ArrayList<AuditInfo>();
						infos.add(ai);
						AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
						autoAuditRecord.setCustomer(infos);
						if (isPublic) {
							autoAuditRecord.setAutoAuditType(AutoAuditType.EXIST.getValue());
							result.clear();
							result.add(autoAuditRecord);
							return result;
						} else {
							autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
							result.add(autoAuditRecord);
						}
					}
				}
			}
		}
		// 公司名质疑,只比对公共库、自有库
		if (allSaleList != null && allSaleList.size() > 0) {
			Long[] saleDataIDs = new Long[allSaleList.size()];
			for (int i = 0; i < allSaleList.size(); i++) {
				SaleData saledata = allSaleList.get(i);
				saleDataIDs[i] = saledata.getId();
			}
			Map<Long, Long> posIDsMap = null;
			// if (!isPanguPoseId(posid)) {
			// posIDsMap = customerDao.findCustPosIdById(saleDataIDs);
			// } else
			// {
			posIDsMap = customerService.findCustIdPosIdByCustIds(saleDataIDs);
			posIDsMap.putAll(custService.findCustIdPosIdByCustIdList(Arrays.asList(saleDataIDs)));
			// }

			for (SaleData saledata : allSaleList) {
				// 如果修改的是本条记录
				if (custid != null && custid.equals(saledata.getId())) {
					continue;
				}
				// 如果已经成单
				if (isSaleAlreadySign(saledata.getId())) {
					continue;
				}
				// 只检验本运营单位内的
				if (posid == null || posid.equals(0L) || !posid.equals(posIDsMap.get(saledata.getId()))) {
					continue;
				}
				Long follower = getFollowerId(saledata.getId());
				AuditInfo ai = new AuditInfo();
				ai.setId(saledata.getId());
				ai.setName(saledata.getCompanyname());
				ai.setAutoAuditSource((follower == null || follower.intValue() == 0) ? AutoAuditSourceType.PERSONAL_LIB.getValue() : AutoAuditSourceType.PERSONAL_LIB.getValue());
				ai.setRemark("CustName [" + custFullName + "]");
				List<AuditInfo> infos = new ArrayList<AuditInfo>();
				infos.add(ai);
				AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
				autoAuditRecord.setAutoAuditType(AutoAuditType.COMPANY_NAME_OPPUGN.getValue());
				autoAuditRecord.setCustomer(infos);
				result.add(autoAuditRecord);
			}
		}

		return result;
	}

	/**
	 * 去除非核心词
	 * 
	 * @param sdl
	 * @param custFullName
	 * @return
	 * @author cm
	 * @created 2010-6-18 下午02:41:18
	 * @lastModified
	 * @history
	 */
	private List<SaleData> filterSaleDataList(List<SaleData> sdl, String custFullName) {
		if (sdl != null) {
			log.info("before filterSaleDataList,list size=" + sdl.size());
		} else {
			log.info("before filterSaleDataList,list size=0");
		}
		List<SaleData> ret = new ArrayList<SaleData>();
		custFullName = tinyseMgr.removeNoneCoreWord(custFullName);
		if (sdl != null) {
			for (SaleData sd : sdl) {
				String name = tinyseMgr.removeNoneCoreWord(sd.getCompanyname());
				if (custFullName != null && name != null && custFullName.replaceAll(companyComparePattern, "").length() == name.replaceAll(companyComparePattern, "").length()) {
					ret.add(sd);
				}
			}
		}
		if (ret != null) {
			log.info("after filterSaleDataList,list size=" + ret.size());
		} else {
			log.info("after filterSaleDataList,list size=0");
		}
		return ret;
	}

	/**
	 * 去除非核心词和地域词
	 * 
	 * @param sdl
	 * @param custFullName
	 * @return
	 * @author cm
	 * @created 2010-6-18 下午02:40:53
	 * @lastModified
	 * @history
	 */
	private List<SaleData> filterSaleDataAreaWordList(List<SaleData> sdl, String custFullName, Long posId) {
		if (sdl != null) {
			log.info("before filterSaleDataAreaWordList,list size=" + sdl.size());
		} else {
			log.info("before filterSaleDataAreaWordList,list size=0");
		}
		List<SaleData> ret = new ArrayList<SaleData>();
		custFullName = tinyseMgr.removeNoneCoreWord(custFullName);
		custFullName = tinyseMgr.removeAreaWord(custFullName, posId);
		if (sdl != null) {
			for (SaleData sd : sdl) {
				String name = tinyseMgr.removeNoneCoreWord(sd.getCompanyname());
				name = tinyseMgr.removeAreaWord(name, posId);
				if (custFullName != null && name != null && custFullName.replaceAll(companyComparePattern, "").length() == name.replaceAll(companyComparePattern, "").length()) {
					ret.add(sd);
				}
			}
		}
		if (ret != null) {
			log.info("after filterSaleDataAreaWordList,list size=" + ret.size());
		} else {
			log.info("after filterSaleDataAreaWordList,list size=0");
		}
		return ret;
	}

	/**
	 * 输入自由库的客户资料id,请确认已经是自由库的客户资料，调用isSalePublic返回 是否已成单
	 */
	private boolean isSaleAlreadySign(Long id) {
		if (id == null || id.longValue() <= 0) {
			log.info("isSaleAlreadySign---id is null");
			return false;
		}
		Map<String, Object> map = cachedCustMap.get().get(id);
		if (map != null) {
			return (Boolean) map.get("isSigned");
		}
		if (BusiUtils.isPanguCustId(id)) {
			Cust cust = custService.findById(id);
			if (cust != null) {
				{
					Map<String, Object> propMap = new HashMap<String, Object>();
					propMap.put("custId", cust.getId());
					propMap.put("isSigned", cust.getStat1().equals(Constant.PANGU_STAT1_CONTRACT_07));
					propMap.put("followId", cust.getInUcid());
					propMap.put("fullName", cust.getFullName());
					cachedCustMap.get().put(cust.getId(), propMap);
				}
				if (cust.getStat1().equals(Constant.PANGU_STAT1_CONTRACT_07)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			Customer customer = customerService.findById(id);
			if (customer != null) {

				if (customer.getCustStat1().intValue() == Constant.SALE_SIGN) {
					log.info("id: " + id + " AlreadySign");
					return true;
				} else {
					return false;
				}
			}
			Cust cust = custService.findById(id);
			if (cust != null) {
				{
					Map<String, Object> propMap = new HashMap<String, Object>();
					propMap.put("custId", cust.getId());
					propMap.put("isSigned", cust.getStat1().equals(Constant.PANGU_STAT1_CONTRACT_07));
					propMap.put("followId", cust.getInUcid());
					propMap.put("fullName", cust.getFullName());
					cachedCustMap.get().put(cust.getId(), propMap);
				}
				if (cust.getStat1().equals(Constant.PANGU_STAT1_CONTRACT_07)) {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	/**
	 * 根据posid判断岗位是否是盘古系统的
	 * 
	 * @param posId
	 * @return
	 * @author cm
	 * @created 2010-9-23 下午02:59:02
	 * @lastModified
	 * @history
	 */
	// TODO Anders Zhu : 重构函数
	private boolean isPanguPosId(Long posId) {
		return (Utils.isNotNull(panguPosIdList) && panguPosIdList.contains(posId));
	}

	// TODO Anders Zhu : 重构函数
	private List<AutoAuditRecord> auditPhone(Set<String> phoneList, Long custid) {
		List<AutoAuditRecord> result = new LinkedList<AutoAuditRecord>();

		if (phoneList == null) {
			log.info("In function auditPhone phoneList==null");
			return null;
		}
		// 客户电话
		for (String phone : phoneList) {
			List<Long> ids = auditService.listMatchPhoneContact(phone);

			if (ids == null || ids.size() == 0) {
				continue;
			}

			Set<Long> cids = new HashSet<Long>(ids);
			// 删除客户本身
			if (custid != null) {
				cids.remove(custid);
			}

			// 未匹配到客户
			if (cids == null || cids.size() == 0) {
				continue;
			}

			Iterator<Long> itr = cids.iterator();
			List<Long> list = new ArrayList<Long>();
			while (itr.hasNext()) {
				list.add(itr.next());
			}
			Long[] custIds = list.toArray(new Long[0]);

			List<Map<String, Object>> custList = custService.findCustIdStatInUcidFullNameByCustIdList(Arrays.asList(custIds));
			for (Map<String, Object> map : custList) {
				Map<String, Object> propMap = new HashMap<String, Object>();
				propMap.put("custId", map.get(FieldConstant.ID));
				propMap.put("isSigned", map.get(FieldConstant.STAT_1).equals(Constant.PANGU_STAT1_CONTRACT_07));
				propMap.put("followId", map.get(FieldConstant.IN_UCID));
				propMap.put("fullName", map.get(FieldConstant.FULL_NAME));
				cachedCustMap.get().put((Long) map.get(FieldConstant.ID), propMap);
			}

			Map<Long, String> custNameMap = new HashMap<Long, String>();
			for (Long custId : custIds) {
				Map map = cachedCustMap.get().get(custId);
				if (map != null) {
					custNameMap.put((Long) map.get("custId"), (String) map.get("fullName"));
				}
			}
			custNameMap.putAll(customerService.findCustIdFullNameByCustIds(custIds));
			List<AuditInfo> infos = new ArrayList<AuditInfo>();
			{
				for (Long custId : list) {
					if (isSaleAlreadySign(custId)) {
						continue;
					}
					AuditInfo ai = new AuditInfo();
					ai.setId(custId);
					ai.setName((String) custNameMap.get(custId));
					Long follower = getFollowerId(custId);
					// 公共库
					if (follower == null || follower.intValue() == 0)
						ai.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
					else
						ai.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());

					ai.setRemark("Phone [" + phone + "]");
					infos.add(ai);
				}
			}
			if (infos != null && infos.size() > 0) {
				AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
				autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
				autoAuditRecord.setCustomer(infos);

				result.add(autoAuditRecord);
			}
		}

		// shifen
		for (String ph : phoneList) {
			List<ShifenData> phlist = tinyseMgr.queryShifenDataWithWordPhrase(ph, Constant.TINYSE_QUERY_LIMIT);

			// 匹配到电话号码
			if (phlist != null && phlist.size() > 0) {

				for (ShifenData sfdata : phlist) {
					AuditInfo ai = new AuditInfo();
					// ID
					ai.setId(sfdata.getId());
					// 公司真实名称 > 公司名称 > 帐号
					String name = sfdata.getRealcompanyname();
					if (StringUtils.isEmpty(name)) {
						name = sfdata.getCompanyname();
					}
					if (StringUtils.isEmpty(name)) {
						name = sfdata.getCustomername();
					}
					ai.setName(name);
					ai.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());
					ai.setRemark("Phone [" + ph + "]");

					List<AuditInfo> infos = new ArrayList<AuditInfo>();
					infos.add(ai);

					AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
					autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
					autoAuditRecord.setCustomer(infos);

					result.add(autoAuditRecord);
					// return result;
				}
			}
		}

		return result;
	}

	/***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * 对List<AutoAuditRecordEx> 按照：季节性备案、老户新开、一户多开、 质疑,公司名质疑 优先级进行排序 并对custid 去重 *********************************************************************
	 * 
	 * @param List
	 *            <AutoAuditRecordEx>
	 * 
	 * @return 排序之后的结果
	 */
	// TODO Anders Zhu : 重构函数
	private List<AutoAuditRecord> sortAutoAuditRecordList(List<AutoAuditRecord> list, Long[] posids) {
		List<AutoAuditRecord> result = new LinkedList<AutoAuditRecord>();
		if (list == null) {
			return result;
		}
		Set<Long> sfset = new HashSet<Long>();
		Set<Long> saleset = new HashSet<Long>();

		// 季节性备案 直接来源季节性备案 间接shifen
		List<AuditInfo> auditInfos = new LinkedList<AuditInfo>();
		for (AutoAuditRecord autoAuditRecord : list) {
			if (autoAuditRecord.getAutoAuditType() == AutoAuditType.SEASON_FILE.getValue()) {
				List<AuditInfo> ais = autoAuditRecord.getCustomer();
				if (ais != null) {
					for (AuditInfo ai : ais) {
						if (auditInfos.size() < Constant.RETURN_MAX_SIZE) {
							if (sfset.add(ai.getId())) {
								auditInfos.add(ai);
							}
						}
					}
				}
			}
		}
		if (auditInfos != null && auditInfos.size() > 0) {
			AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
			autoAuditRecord.setAutoAuditType(AutoAuditType.SEASON_FILE.getValue());
			autoAuditRecord.setCustomer(auditInfos);
			result.add(autoAuditRecord);
		}

		// 老户新开 来源shifen
		auditInfos = new LinkedList<AuditInfo>();
		for (AutoAuditRecord autoAuditRecord : list) {
			if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OLD_TO_NEW.getValue()) {
				List<AuditInfo> ais = autoAuditRecord.getCustomer();
				if (ais != null) {
					for (AuditInfo ai : ais) {
						if (auditInfos.size() < Constant.RETURN_MAX_SIZE) {
							if (sfset.add(ai.getId())) {
								auditInfos.add(ai);
							}
						}
					}
				}
			}
		}

		if (auditInfos != null && auditInfos.size() > 0) {
			AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
			autoAuditRecord.setAutoAuditType(AutoAuditType.OLD_TO_NEW.getValue());
			autoAuditRecord.setCustomer(auditInfos);
			result.add(autoAuditRecord);
		}

		// 一户多开 来源sale
		auditInfos = new LinkedList<AuditInfo>();
		for (AutoAuditRecord autoAuditRecord : list) {
			if (autoAuditRecord.getAutoAuditType() == AutoAuditType.ONE_TO_MANY.getValue()) {
				List<AuditInfo> ais = autoAuditRecord.getCustomer();
				if (ais != null) {
					for (AuditInfo ai : ais) {
						if (auditInfos.size() < Constant.RETURN_MAX_SIZE) {
							if (saleset.add(ai.getId())) {
								auditInfos.add(ai);
							}
						}
					}
				}
			}
		}
		if (auditInfos != null && auditInfos.size() > 0) {
			AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
			autoAuditRecord.setAutoAuditType(AutoAuditType.ONE_TO_MANY.getValue());
			autoAuditRecord.setCustomer(auditInfos);
			result.add(autoAuditRecord);
		}

		// 质疑 shifen sale,质疑的显示20条记录
		auditInfos = new LinkedList<AuditInfo>();
		int oppugn_max_size = 20;
		for (AutoAuditRecord autoAuditRecord : list) {
			if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OPPUGN.getValue()) {
				List<AuditInfo> ais = autoAuditRecord.getCustomer();

				// 首先添加sale中的本运营单位的
				if (ais != null) {
					Set<Long> custIds = new HashSet<Long>();
					for (int i = 0; i < ais.size(); i++) {
						if (!ais.get(i).getAutoAuditSource().equals(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue())) {
							custIds.add(ais.get(i).getId());
						}
					}
					Map<Long, Long> posIDsMap = customerService.findCustIdPosIdByCustIds(custIds.toArray(new Long[0]));
					posIDsMap.putAll(custService.findCustIdPosIdByCustIdList(new ArrayList<Long>(custIds)));

					for (AuditInfo ai : ais) {
						if (auditInfos.size() < oppugn_max_size) {
							if (ai.getAutoAuditSource().equals(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue())) {
								// 来源shifen nothing to do
							} else {
								for (Long posid : posids) {
									if (posid != null && posid.equals(posIDsMap.get(ai.getId())) && saleset.add(ai.getId())) {
										auditInfos.add(ai);
										break;
									}
								}
							}
						}
					}

					for (AuditInfo ai : ais) {
						if (auditInfos.size() < oppugn_max_size) {
							if (ai.getAutoAuditSource().equals(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue())) {
								if (sfset.add(ai.getId())) {
									auditInfos.add(ai);
								}
							} else {
								if (saleset.add(ai.getId())) {
									auditInfos.add(ai);
								}
							}
						}
					}
				}
			}
		}

		if (auditInfos != null && auditInfos.size() > 0) {
			AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
			autoAuditRecord.setAutoAuditType(AutoAuditType.OPPUGN.getValue());
			autoAuditRecord.setCustomer(auditInfos);
			result.add(autoAuditRecord);
		}
		// 添加公司名质疑
		auditInfos = new LinkedList<AuditInfo>();
		for (AutoAuditRecord autoAuditRecord : list) {
			if (autoAuditRecord.getAutoAuditType() == AutoAuditType.COMPANY_NAME_OPPUGN.getValue()) {
				List<AuditInfo> ais = autoAuditRecord.getCustomer();
				if (ais != null) {
					for (AuditInfo ai : ais) {
						if (auditInfos.size() < Constant.RETURN_MAX_SIZE) {
							if (saleset.add(ai.getId())) {
								auditInfos.add(ai);
							}
						}
					}
				}
			}
		}
		if (auditInfos != null && auditInfos.size() > 0) {
			AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
			autoAuditRecord.setAutoAuditType(AutoAuditType.COMPANY_NAME_OPPUGN.getValue());
			autoAuditRecord.setCustomer(auditInfos);
			result.add(autoAuditRecord);
		}

		return result;
	}

	// TODO Anders Zhu : 重构函数,public函数
	public List<AutoAuditRecord> auditCust(Customer cust, Long[] posids) throws AutoAuditException {

		if (cust == null) {
			throw new AutoAuditException("输入参数不能为空");
		}
		Long custid = cust.getCustId();

		// 跟进人
		Long ucid = null;
		if (custid == null) {
			// 新提交
			ucid = cust.getAddUser();
		} else {
			// 修改
			Long followID = followAssignService.getFollowerId(custid);
			if (followID != null && followID != 0) {
				ucid = followID;
			} else {
				ucid = cust.getUpdUser();
			}
		}

		// 客户类型
		Short custType = cust.getCustType();
		if (custType == null) {
			throw new AutoAuditException("客户类型不能为空");
		}

		// 公司名称
		String custName = null;
		String custFullName = null;
		if (custType.intValue() == CustType.SPECIAL_ENTERPRISE.getValue()) {
			custName = cust.getCustName();
			custFullName = cust.getCustFullName();
		} else if (custType.intValue() == CustType.ADVERTISING_AGENCY.getValue()) {
			custName = cust.getCustBranchName();
			custFullName = custName;
		} else {
			custName = cust.getCustName();
			custFullName = custName;
		}
		if (custFullName == null) {
			throw new AutoAuditException("公司名称不能为空");
		}

		// 个人称谓校验
		if (custType.intValue() == CustType.PERSONAL_CUSTOMER.getValue()) {
			boolean flag = validateCustName(custName);
			if (!flag) {
				throw new AutoAuditException("个人用户名称不合法");
			}
		}

		// 去除所有的符号
		if (custFullName != null) {
			custFullName = custFullName.replaceAll(companyComparePattern, "");
		}
		if (custName != null) {
			custName = custName.replaceAll(companyComparePattern, "");
		}

		// 普通企业类型的客户资料，公司名称栏字符不低于5个
		if (custType.intValue() == CustType.GENERAL_ENTERPRISE.getValue() && custName.length() < 2) {
			throw new IllegalArgumentException("普通企业类型的客户资料，公司名称长度不能低于2个");
		}

		// 电话号码
		Set<String> phoneList = new HashSet<String>();
		List<CustContact> contactList = cust.getContactList();
		if (contactList != null && contactList.size() > 0) {
			for (CustContact cc : contactList) {
				// TODO Anders Zhu : 原来用的轩辕的tb_cust_contact表，其中有del_flag，但是盘古的表中没有该字段，所以用disabled_flag代替
				if (FlagType.ENABLE.getValue().equals(cc.getDelFlag())) {
					List<Phone> phList = cc.getPhoneList();
					if (phList != null && phList.size() > 0) {
						for (Phone p : phList) {
							if (FlagType.ENABLE.getValue().equals(p.getDisabled())) {
								String phone = p.getFullPhone();
								phoneList.add(phone);
							}
						}
					}
				}
			}
		}
		if (phoneList.size() == 0) {
			throw new AutoAuditException("联系人或联系电话不能全部无效");
		}

		// 审核结果
		List<AutoAuditRecord> result = new LinkedList<AutoAuditRecord>();

		// URL
		String url = null;
		String domain = null;
		List<CustUrl> urlList = cust.getUrlList();
		if (urlList != null && urlList.size() > 0) {
			url = urlList.get(0).getCustUrlName();
			domain = urlList.get(0).getDomain();
		}
		// 黑名单审核
		AutoAuditRecord ret = auditBlacklist(custFullName, url, phoneList);
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			result.clear();
			result.add(ret);
			return result;
		}

		// 季节性备案
		ret = auditSeasonFileCustlist(custName, url, domain, phoneList, posids);
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			result.clear();
			result.add(ret);
			return result;
		}
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.SEASON_FILE.getValue()) {
			result.add(ret);
		}

		if (url != null) {
			if (domain == null) {
				throw new AutoAuditException("URL格式不正确，请确认后再输入");
			}
			ret = auditShifenCustWhitelist(url, ucid, custid);
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.PASS.getValue()) {
				result.clear();
				result.add(ret);
				return result;
			}
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
				result.clear();
				result.add(ret);
				return result;
			}

			ret = auditURL(url, domain, custid, posids, null);
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
				result.clear();
				result.add(ret);
				return result;
			}
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.OPPUGN.getValue()) {
				result.add(ret);
			}
		}

		List<AutoAuditRecord> rets = auditOldCust(custFullName, custName, custType, phoneList);
		// 添加老户新开记录
		if (rets != null && rets.size() > 0) {
			for (AutoAuditRecord autoAuditRecord : rets) {
				if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OLD_TO_NEW.getValue()) {
					result.add(autoAuditRecord);
				}
			}
		}

		rets = auditSaleCust(custid, ucid, custFullName, custName, custType, phoneList, url, cust.getPoseId());
		// 添加重复、质疑、一户多开记录
		if (rets != null && rets.size() > 0) {
			for (AutoAuditRecord autoAuditRecord : rets) {
				if (autoAuditRecord.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
					result.clear();
					result.add(autoAuditRecord);
					return result;
				} else if (autoAuditRecord.getAutoAuditType() == AutoAuditType.ONE_TO_MANY.getValue()) {
					result.add(autoAuditRecord);
				} else if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OPPUGN.getValue()) {
					result.add(autoAuditRecord);
				}
				// 公司名质疑
				else if (autoAuditRecord.getAutoAuditType() == AutoAuditType.COMPANY_NAME_OPPUGN.getValue()) {
					result.add(autoAuditRecord);
				}

			}
		}

		rets = auditPhone(phoneList, custid);
		// 添加质疑记录
		if (rets != null && rets.size() > 0) {
			for (AutoAuditRecord autoAuditRecord : rets) {
				if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OPPUGN.getValue()) {
					result.add(autoAuditRecord);
				}
			}
		}

		// 排序
		result = sortAutoAuditRecordList(result, posids);
		return result;
	}

	/**
	 * 盘古唯一性验证
	 * 
	 * @param cust
	 * @param posids
	 * @return
	 * @throws AutoAuditException
	 * @author cm
	 * @created 2010-9-13 上午11:59:40
	 * @lastModified
	 * @history
	 */
	// TODO Anders Zhu : 重构函数,public函数
	public List<AutoAuditRecord> auditCustRmi(CustomerVO cust, Long[] posids) throws AutoAuditException {
		if (cust == null) {
			throw new AutoAuditException("输入参数不能为空");
		}
		Long custid = cust.getCustId();
		Long followID = cust.getInUcid();
		// 跟进人
		Long ucid = null;
		if (custid == null) {
			// 新提交
			ucid = cust.getAddUcid();
		} else {
			// 修改
			if (followID != null && followID != 0) {
				ucid = followID;
			} else {
				ucid = cust.getUpdUcid();
			}
		}
		// 客户类型
		Short custType = cust.getCustType();
		if (custType == null) {
			throw new AutoAuditException("客户类型不能为空");
		}

		// 公司名称
		String custName = null;
		String custFullName = null;
		// 特殊企业
		if (custType.intValue() == CustType.SPECIAL_ENTERPRISE.getValue()) {
			custName = cust.getCustName();
			custFullName = cust.getFullName();
		}
		// 广告代理
		else if (custType.intValue() == CustType.ADVERTISING_AGENCY.getValue()) {
			custName = cust.getBranchName();
			custFullName = custName;
		} else {
			custName = cust.getCustName();
			custFullName = custName;
		}
		if (custFullName == null) {
			throw new AutoAuditException("公司名称不能为空");
		}

		// 个人称谓校验
		if (custType.intValue() == CustType.PERSONAL_CUSTOMER.getValue()) {
			boolean flag = validateCustName(custName);
			if (!flag) {
				throw new AutoAuditException("个人用户名称不合法");
			}
		}

		// 去除所有的符号
		if (custFullName != null) {
			custFullName = custFullName.replaceAll(companyComparePattern, "");
		}
		if (custName != null) {
			custName = custName.replaceAll(companyComparePattern, "");
		}

		// 普通企业类型的客户资料，公司名称栏字符不低于2个
		if (custType.intValue() == CustType.GENERAL_ENTERPRISE.getValue() && custName.length() < 2) {
			throw new IllegalArgumentException("普通企业类型的客户资料，公司名称长度不能低于2个");
		}

		// 电话号码
		Set<String> phoneList = new HashSet<String>();
		List<CustContactVO> contactList = cust.getCustContacts();
		if (contactList != null && contactList.size() > 0) {
			for (CustContactVO cc : contactList) {
				if (FlagType.ENABLE.getValue().equals(cc.getDisabledFlag())) {
					List<CustContactPhoneVO> phList = cc.getContactPhones();
					if (phList != null && phList.size() > 0) {
						for (CustContactPhoneVO p : phList) {
							if (FlagType.ENABLE.getValue().equals(p.getDisabledFlag())) {
								String phone = p.getFullPhone();
								phoneList.add(phone);
							}
						}
					}
				}
			}
		}
		if (phoneList.size() == 0) {
			throw new AutoAuditException("联系电话不能为空");
		}

		// 审核结果
		List<AutoAuditRecord> result = new LinkedList<AutoAuditRecord>();

		// URL
		String url = cust.getSiteUrl();
		String domain = cust.getSiteDomain();

		// 黑名单审核
		AutoAuditRecord ret = auditBlacklist(custFullName, url, phoneList);
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			result.clear();
			result.add(ret);
			return result;
		}

		// 季节性备案
		ret = auditSeasonFileCustlist(custName, url, domain, phoneList, posids);
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			result.clear();
			result.add(ret);
			return result;
		}
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.SEASON_FILE.getValue()) {
			result.add(ret);
		}

		if (url != null) {
			if (domain == null) {
				throw new AutoAuditException("URL格式不正确，请确认后再输入");
			}
			ret = auditShifenCustWhitelist(url, ucid, custid);
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.PASS.getValue()) {
				result.clear();
				result.add(ret);
				return result;
			}
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
				result.clear();
				result.add(ret);
				return result;
			}

			ret = auditURL(url, domain, custid, posids, cust.getInUcid());
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
				result.clear();
				result.add(ret);
				return result;
			}
			if (ret != null && ret.getAutoAuditType() == AutoAuditType.OPPUGN.getValue()) {
				result.add(ret);
			}
		}

		List<AutoAuditRecord> rets = auditOldCust(custFullName, custName, custType, phoneList);
		// 添加老户新开记录
		if (rets != null && rets.size() > 0) {
			for (AutoAuditRecord autoAuditRecord : rets) {
				if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OLD_TO_NEW.getValue()) {
					result.add(autoAuditRecord);
				}
			}
		}

		rets = auditSaleCust(custid, ucid, custFullName, custName, custType, phoneList, url, cust.getPosid());
		// 添加重复、质疑、一户多开记录
		if (rets != null && rets.size() > 0) {
			for (AutoAuditRecord autoAuditRecord : rets) {
				if (autoAuditRecord.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
					result.clear();
					result.add(autoAuditRecord);
					return result;
				} else if (autoAuditRecord.getAutoAuditType() == AutoAuditType.ONE_TO_MANY.getValue()) {
					result.add(autoAuditRecord);
				} else if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OPPUGN.getValue()) {
					result.add(autoAuditRecord);
				}
				// 公司名质疑
				else if (autoAuditRecord.getAutoAuditType() == AutoAuditType.COMPANY_NAME_OPPUGN.getValue()) {
					result.add(autoAuditRecord);
				}
			}
		}

		rets = auditPhone(phoneList, custid);
		// 添加质疑记录
		if (rets != null && rets.size() > 0) {
			for (AutoAuditRecord autoAuditRecord : rets) {
				if (autoAuditRecord.getAutoAuditType() == AutoAuditType.OPPUGN.getValue()) {
					result.add(autoAuditRecord);
				}
			}
		}
		// 排序
		result = sortAutoAuditRecordList(result, posids);
		return result;
	}

	/**
	 * 判断URL是否合法，库中是否已存在
	 * 
	 * 首先调用季节性备案、老客户白名单判断流程，然后原有的validateURL实现
	 * 
	 * @param url
	 * @param custid
	 * @param posids
	 * @return false 不通过(重复,为空),true 通过验证 如果输入参数非法，返回值size=0
	 */
	// TODO Anders Zhu : 重构函数，public函数
	public List<AutoAuditRecord> validateURL(String url, Long addUser, Long custid, Long[] posids) throws AutoAuditException {
		// 审核结果
		List<AutoAuditRecord> result = new LinkedList<AutoAuditRecord>();
		if (url == null || url.length() == 0) {
			return result;
		}
		// 黑名单审核
		AutoAuditRecord ret = auditBlacklist(ValidType.URL, url);
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			result.add(ret);
			return result;
		}
		// 季节性备案
		ret = auditSeasonFileCustlist(null, url, null, null, posids);
		if (ret == null) {
			return result;
		}
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			result.add(ret);
			return result;
		}
		// 老客户白名单
		ret = auditShifenCustWhitelist(url, addUser, custid);
		if (ret == null) {
			return result;
		}
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.EXIST.getValue()) {
			result.add(ret);
			return result;
		}
		if (ret != null && ret.getAutoAuditType() == AutoAuditType.PASS.getValue()) {
			result.add(ret);
			return result;
		}
		// 原有逻辑流程

		String domain = URLUtils.getDomain(url);
		if (domain == null) {
			return result;
		}

		boolean exist = urlWhitelistService.isDomainAndPosIdsExist(domain, posids);
		if (exist) {
			// sale
			List<Map<String, Object>> salelist = auditService.listMatchCustUrl(url);
			if (salelist != null && salelist.size() > 0) {
				List<AuditInfo> infos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : salelist) {
					if (custid != null && custid.equals(mp.get("cust_id"))) {
						continue;
					}
					// if (isSaleAlreadySign((Long) mp.get("cust_id"))) {
					// continue;
					// }
					AuditInfo ai = new AuditInfo();
					ai.setId((Long) mp.get("cust_id"));
					ai.setName((String) mp.get("cust_full_name"));
					Long follower = getFollowerId(ai.getId());
					// 公共库
					if (follower == null || follower.intValue() == 0)
						ai.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
					else
						ai.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());

					ai.setRemark("URL [" + url + "]");
					infos.add(ai);
					break;
				}
				if (infos != null && infos.size() > 0) {
					ret = new AutoAuditRecord();
					ret.setAutoAuditType(AutoAuditType.EXIST.getValue());
					ret.setCustomer(infos);

					result.add(ret);
					return result;
				}
			}

			// shifen
			List<Map<String, Object>> sflist = shifenCustomerService.equalSiteUrl(url);
			if (sflist != null && sflist.size() > 0) {
				List<AuditInfo> infos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : sflist) {
					// if (custid != null
					// && custid.equals((Long) mp.get("customerd"))) {
					// continue;
					// }
					AuditInfo ai = new AuditInfo();
					ai.setId((Long) mp.get("customerd"));

					String name = (String) mp.get("realcompanyname");
					if (StringUtils.isEmpty(name)) {
						name = (String) mp.get("companyname");
					}
					if (StringUtils.isEmpty(name)) {
						name = (String) mp.get("customername");
					}
					ai.setName(name);
					ai.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());

					ai.setRemark("URL [" + url + "]");
					infos.add(ai);
					break;
				}
				if (infos != null && infos.size() > 0) {
					ret = new AutoAuditRecord();
					ret.setAutoAuditType(AutoAuditType.EXIST.getValue());
					ret.setCustomer(infos);

					result.add(ret);
					return result;
				}
			}

		} else {
			// sale
			List<Map<String, Object>> salelist = auditService.listMatchDomain(domain);
			if (salelist != null && salelist.size() > 0) {
				List<AuditInfo> infos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : salelist) {
					if (custid != null && custid.equals(mp.get("cust_id"))) {
						continue;
					}
					// if (isSaleAlreadySign((Long) mp.get("cust_id"))) {
					// continue;
					// }
					AuditInfo ai = new AuditInfo();
					ai.setId((Long) mp.get("cust_id"));
					ai.setName((String) mp.get("cust_full_name"));
					Long follower = getFollowerId(ai.getId());
					// 公共库
					if (follower == null || follower.intValue() == 0)
						ai.setAutoAuditSource(AutoAuditSourceType.PUBLIC_LIB.getValue());
					else
						ai.setAutoAuditSource(AutoAuditSourceType.PERSONAL_LIB.getValue());

					ai.setRemark("Domain [" + domain + "]");
					infos.add(ai);
					break;
				}
				if (infos != null && infos.size() > 0) {
					ret = new AutoAuditRecord();
					ret.setAutoAuditType(AutoAuditType.EXIST.getValue());
					ret.setCustomer(infos);

					result.add(ret);
					return result;
				}

			}

			// shifen
			List<Map<String, Object>> sflist = shifenCustomerService.equalUrlDomain(domain);
			if (sflist != null && sflist.size() > 0) {
				List<AuditInfo> infos = new ArrayList<AuditInfo>();
				for (Map<String, Object> mp : sflist) {
					// if (custid != null
					// && custid.equals((Long) mp.get("customerd"))) {
					// continue;
					// }
					AuditInfo ai = new AuditInfo();
					ai.setId((Long) mp.get("customerd"));

					String name = (String) mp.get("realcompanyname");
					if (StringUtils.isEmpty(name)) {
						name = (String) mp.get("companyname");
					}
					if (StringUtils.isEmpty(name)) {
						name = (String) mp.get("customername");
					}
					ai.setName(name);
					ai.setAutoAuditSource(AutoAuditSourceType.SHIFEN_OLD_CUST.getValue());

					ai.setRemark("Domain [" + domain + "]");
					infos.add(ai);
					break;
				}
				if (infos != null && infos.size() > 0) {
					ret = new AutoAuditRecord();
					ret.setAutoAuditType(AutoAuditType.EXIST.getValue());
					ret.setCustomer(infos);

					result.add(ret);
					return result;
				}
			}
		}
		ret = new AutoAuditRecord();
		ret.setAutoAuditType(AutoAuditType.PASS.getValue());
		result.add(ret);
		return result;
	}

	/**
	 * 是否为老客户,如果是老客户返回true,否则返回false
	 * 
	 * @param custName
	 * @return
	 * @author cm
	 * @created 2010-9-26 下午02:28:26
	 * @lastModified
	 * @history
	 */
	// TODO Anders Zhu : 重构函数，public函数
	public boolean auditCustName(String custName) {
		if (custName == null)
			return false;
		List<Long> sfids = shifenCustomerService.equalCompanyName(custName);
		if (sfids != null && sfids.size() > 0)
			return true;

		return false;
	}

	/**
	 * 1) 只是提示信息，并不影响客户资料的保存。 2) “被保护”客户：状态为“新提交”、“确认中”、“跟进中”、“订单审核中”。 3) 检验范围，只检验本运营单位内部的客户资料。 4) 需检验的“公司名称”的规则： A. 普通企业：一个框； B. 特殊企业：第一个框，两框合并； C. 个人类型：不比较； D. 广告代理公司：后一个框。 5) 检验的规则： A. 切词匹配，核心词 B. 被包含的即判定为相同（即，不判断长度）
	 * 
	 * @param custName
	 * @param custBranchName
	 * @param custId
	 * @param posid
	 * @return
	 */
	// TODO Anders Zhu : 重构函数，public函数
	public boolean getSelfAuditCustName(String custName, String custBranchName, Long custId, Long posid) {
		if (StringUtils.isNotBlank(custName)) {
			custName = custName.replaceAll(companyComparePattern, "");
		}
		if (StringUtils.isNotBlank(custBranchName)) {
			custBranchName = custBranchName.replaceAll(companyComparePattern, "");
		}
		String firstName = custName;
		String secordName = custName + (custBranchName == null ? "" : custBranchName);
		if (StringUtils.isBlank(firstName)) {
			firstName = custBranchName;
			secordName = null;
		}
		if (StringUtils.isBlank(firstName)) {
			throw new IllegalArgumentException("客户名称不能为空");
		}
		if (posid == null) {
			throw new IllegalArgumentException("运营单位岗位ID不能为空");
		}
		if (StringUtils.isNotBlank(firstName)) {
			firstName = tinyseMgr.removeNoneCoreWord(firstName);
			if (StringUtils.isNotBlank(firstName)) {
				firstName = tinyseMgr.removeAreaWord(firstName, posid);
			}
		}

		if (StringUtils.isNotBlank(secordName)) {
			secordName = tinyseMgr.removeNoneCoreWord(secordName);
			if (StringUtils.isNotBlank(secordName)) {
				secordName = tinyseMgr.removeAreaWord(secordName, posid);
			}
		}
		if (StringUtils.isBlank(firstName)) {
			throw new IllegalArgumentException("客户名称不能全都是非核心词或地域词");
		}
		boolean isProtected = getCustSelfAuditCustName(firstName, posid, custId);
		if (isProtected == false) {
			if (StringUtils.isNotBlank(secordName)) {
				isProtected = getCustSelfAuditCustName(secordName, posid, custId);
			}
			return isProtected;
		} else {
			return isProtected;
		}
	}

	// TODO Anders Zhu : 重构该函数
	private boolean getCustSelfAuditCustName(String custName, Long posid, Long custId) {
		if (StringUtils.isBlank(custName)) {
			throw new IllegalArgumentException("客户名称 不能为空");
		}
		if (posid == null) {
			throw new IllegalArgumentException("运营单位岗位ID不能为空");
		}
		List<SaleData> list = tinyseMgr.querySaleData(custName, 800);
		List<Long> custXuanyuan = custService.findByFullName(custName);
		List<Long> custPg = customerService.equalCustFullName(custName);
		{
			List<Long> custIdList = new ArrayList<Long>();
			for (int i = 0; i < list.size(); i++) {
				SaleData sd = list.get(i);
				if (sd != null && sd.getId() > 0) {
					custIdList.add(sd.getId());
				}
			}
			custIdList.addAll(custXuanyuan);
			custIdList.addAll(custPg);
			// if (!isPanguPoseId(posid)) {
			// boolean panguExist = customerDao.findByCustIdsAndPosid(
			// custIdList, posid, custId);
			// if (panguExist) {
			// return true;
			// } else {
			// return false;
			// }
			// } else
			{
				boolean panguExist = customerService.isCustIdsExist(custIdList, posid, custId);
				if (panguExist) {
					return true;
				} else {
					panguExist = custService.isCustIdListExist(custIdList, posid, custId);
					return panguExist;
				}
			}

		}
	}

	/**
	 * 客户资料审核对比时,根据轩辕系统的客户资料id的集合,得到资料ID,录入时间,分配时间,公司名称,状态,性质的map集合
	 * 
	 * @param custIds
	 * @return
	 * @author cm
	 * @created 2010-9-25 下午02:16:33
	 * @lastModified
	 * @history
	 */
	// TODO Anders Zhu : 重构该函数，public函数
	public List<Map<String, Object>> findCustByCustIds(List<Long> custIds) {
		List<Map<String, Object>> result = customerService.findCustomerFollowDistributeByCustIdList(custIds);
		if (result != null && result.size() > 0) {
			List<Long> userIds = new ArrayList<Long>();
			List<Long> posIds = new ArrayList<Long>();
			for (Map<String, Object> map : result) {
				Long followId = (Long) map.get("follow_id");
				if (followId != null && followId.longValue() != 0) {
					userIds.add(followId);
				}
				Long posId = (Long) map.get("pose_id");
				if (posId != null && posId.longValue() != 0) {
					posIds.add(posId);
				}
			}
			Map<Long, User> userMap = userMgr.getUserMap(userIds);
			Map<Long, Position> posMap = userMgr.getPositionMapByIds(posIds);
			for (Map<String, Object> map : result) {
				map.put("cust_stat_1_label", Constant.CUST_STAT_1_MAP.get(map.get("cust_stat_1")));
				Map<String, String> cust_dic = null;
				try {
					cust_dic = (Map<String, String>) this.getClass().getClassLoader().loadClass("com.baidu.rigel.sale.commons.Globals").getDeclaredField("CUST_DIC").get(null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				map.put("cust_stat_2_label", cust_dic.get(map.get("cust_stat_2") + "," + Constant.GROUP_ID_CUST_STAT_2));
				map.put("cust_type_label", Constant.CUST_TYPE_MAP.get(Integer.valueOf(map.get("cust_type").toString())));
				map.put("follow_label", userMap.get(map.get("follow_id")) == null ? "" : userMap.get(map.get("follow_id")).getUcname());
				map.put("pose_label", posMap.get(map.get("pose_id")) == null ? "" : posMap.get(map.get("pose_id")).getPosname());
			}
		}
		return result;
	}

	/**
	 * 删除盘古中的客户资料
	 * 
	 * @param custId
	 * @author cm
	 * @created 2010-9-26 上午10:29:15
	 * @lastModified
	 * @history
	 */
	// TODO Anders Zhu : 重构该函数，该函数涉及删除操作
	public void deleteCustomerByCustId(Long custId) {
		Customer customer = customerService.findById(custId);
		if (Utils.isNotNull(customer))
			customerService.delete(custId);
	}

	/**
	 * 检查是否客户url是否在老客户白名单中,存在返回true,否则返回false
	 * 
	 * @param customerVO
	 * @return
	 * @author cm
	 * @created 2010-10-13 下午04:03:57
	 * @lastModified
	 * @history
	 */
	// TODO Anders Zhu : 重构该函数，public函数
	public boolean checkInShifenWhitelist(CustomerVO customerVO) {
		String url = customerVO.getSiteUrl();
		// Long ucid = customerVO.getInUcid();
		// Long custid = customerVO.getCustId();
		if (StringUtils.isEmpty(url)) {
			return false;
		}
		ShifenCustWhiteList shifenCustWhiteList = shifenCustWhiteListService.equalUrl(url);
		if (shifenCustWhiteList != null) {
			// List<Map<String, Object>> list = custUrlAuditService.listMatchCustUrl(url);
			// // 得到盘古中的客户资料
			// List<Map<String, Object>> pgList = customerFacade.findByUrl(url);
			// Set<Long> ids = new HashSet<Long>();
			// if (list != null && list.size() > 0) {
			// for (Map<String, Object> mp : list) {
			// ids.add((Long) mp.get("cust_id"));
			// }
			// }
			// if (pgList != null && pgList.size() > 0) {
			// for (Map<String, Object> mp : pgList) {
			// ids.add((Long) mp.get("cust_id"));
			// }
			// }
			//
			// // 去除客户本身
			// if (custid != null) {
			// ids.remove(custid);
			// }
			//
			// if (ids.size() > 0) {
			// // 重复
			// for (Map<String, Object> mp : list) {
			// // 去除客户本身
			// if (custid != null && custid.equals(mp.get("cust_id"))) {
			// continue;
			// }
			// return false;
			// }
			// for (Map<String, Object> mp : pgList) {
			// // 去除客户本身
			// if (custid != null && custid.equals(mp.get("custId"))) {
			// continue;
			// }
			// return false;
			// }
			// } else {
			// // 通过
			// return true;
			// }
			return true;
		}
		return false;
	}

	/**
	 * 根据custid得到客户基本信息
	 * 
	 * @param custId
	 * @return
	 * @author cm
	 * @created 2010-12-7 下午01:57:38
	 * @lastModified
	 * @history
	 */
	// TODO Anders Zhu : 重构该函数，public函数
	public Map<String, Object> findCustByCustId(Long custId) {
		Map<String, Object> result = customerService.findCustIdFullNamePoseIdInputTypeByCustId(custId);
		if (result == null)
			return null;
		List<CustUrl> custUrls = custUrlAuditService.findCustUrlByCustId(custId);
		if (custUrls != null && custUrls.size() >= 1) {
			result.put("custUrl", custUrls.get(0).getCustUrlName());
		}
		Long followId = followAssignService.getFollowerId(custId);
		if (followId != null && followId.longValue() > 0) {
			result.put("followId", followId);
		}
		return result;
	}
}
