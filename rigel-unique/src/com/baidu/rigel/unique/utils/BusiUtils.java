package com.baidu.rigel.unique.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.rigel.service.tinyse.data.SaleData;
import com.baidu.rigel.unique.facade.AuditInfo;
import com.baidu.rigel.unique.facade.AutoAuditRecord;
import com.baidu.rigel.unique.utils.Constant.ValidType;

/**
 * 业务工具类
 * 
 * @author Anders Zhu
 */
public class BusiUtils {
	private static Log log = LogFactory.getLog(BusiUtils.class);

	/**
	 * 如果“公司真实名称”不为null和空字符串，则返回；否则继续判断“公司名称”，依次类推，直至“客户名”
	 * 
	 * @param realCompanyName
	 *            公司真实名称
	 * @param companyName
	 *            公司名称
	 * @param customerName
	 *            客户名
	 * @return 返回“公司真实名称”、“公司名称”和“客户名”三个参数中第一个不为null和空字符串的参数
	 */
	public static String getCustName(String realCompanyName, String companyName, String customerName) {
		return Utils.getFirstNotEmptyParam(realCompanyName, companyName, customerName);
	}

	/**
	 * 将参数id、name和source保存到Map中并返回
	 * 
	 * @param id
	 *            编号
	 * @param name
	 *            名称
	 * @param source
	 *            数据来源
	 * @return 包含编号、名称和数据来源的Map
	 */
	public static Map<String, Object> getIdNameSourceMap(Object id, Object name, Object source) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.CUST_ID, id);
		map.put(Constant.CUST_NAME, name);
		map.put(Constant.CUST_SOURCE, source);
		return map;
	}

	/**
	 * 将参数id、name保存到Map中并返回，自动调用getIdNameSourceMap并保存销售数据来源到Map中
	 * 
	 * @param id
	 *            编号
	 * @param name
	 *            名称
	 * @return 包含编号、名称和销售数据来源的Map
	 */
	public static Map<String, Object> getIdNameSourceMapFromSale(Object id, Object name) {
		return getIdNameSourceMap(id, name, SourceType.CUST_SOURCE_SALE.toString());
	}

	/**
	 * 将参数id、name保存到Map中并返回，自动调用getIdNameSourceMap并保存黑名单数据来源到Map中
	 * 
	 * @param id
	 *            编号
	 * @param name
	 *            名称
	 * @return 包含编号、名称和黑名单数据来源的Map
	 */
	public static Map<String, Object> getIdNameSourceMapFromBlacklist(Object id, Object name) {
		return getIdNameSourceMap(id, name, SourceType.CUST_SOURCE_BLACKLIST.toString());
	}

	/**
	 * 将参数id、name保存到Map中并返回，自动调用getIdNameSourceMap并保存十分数据来源到Map中
	 * 
	 * @param id
	 *            编号
	 * @param name
	 *            名称
	 * @return 包含编号、名称和十分数据来源的Map
	 */
	public static Map<String, Object> getIdNameSourceMapFromShifen(Object id, Object name) {
		return getIdNameSourceMap(id, name, SourceType.CUST_SOURCE_SHIFEN.toString());
	}

	/**
	 * 判断客户是否是盘古客户（true：是；false：否）
	 * 
	 * @param custId
	 *            客户编号
	 * @return 判断结果
	 */
	public static boolean isPanguCustId(Long custId) {
		if (custId > Constant.PANGU_START_CUST_ID)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	/**
	 * 创建AuditInfo对象
	 * 
	 * @param id
	 * @param name
	 * @param src
	 * @param remark
	 * @return 返回AuditInfo对象
	 */
	public static AuditInfo newAuditInfo(Long id, String name, Short src, String remark) {
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setId(id);
		auditInfo.setName(name);
		auditInfo.setAutoAuditSource(src);
		auditInfo.setRemark(remark);
		return auditInfo;
	}

	/**
	 * 更新AuditInfo对象（如果字段为null，则该字段不更新）
	 * 
	 * @param auditInfo
	 * @param id
	 * @param name
	 * @param src
	 * @param remark
	 * @return 返回AuditInfo对象
	 */
	public static AuditInfo updateAuditInfo(AuditInfo auditInfo, Long id, String name, Short src, String remark) {
		if (Utils.isNotNull(id))
			auditInfo.setId(id);
		if (Utils.isNotNull(name))
			auditInfo.setName(name);
		if (Utils.isNotNull(src))
			auditInfo.setAutoAuditSource(src);
		if (Utils.isNotNull(remark))
			auditInfo.setRemark(remark);
		return auditInfo;
	}

	/**
	 * 创建AutoAuditRecord对象
	 * 
	 * @param type
	 * @param auditInfoList
	 * @return 返回AutoAuditRecord对象
	 */
	public static AutoAuditRecord newAutoAuditRecord(Short type, List<AuditInfo> auditInfoList) {
		AutoAuditRecord autoAuditRecord = new AutoAuditRecord();
		autoAuditRecord.setAutoAuditType(type);
		autoAuditRecord.setCustomer(auditInfoList);
		return autoAuditRecord;
	}

	public static AutoAuditRecord updateAutoAuditRecord(AutoAuditRecord autoAuditRecord, Short type, List<AuditInfo> auditInfoList) {
		if (Utils.isNotNull(type))
			autoAuditRecord.setAutoAuditType(type);
		if (Utils.isNotNull(auditInfoList))
			autoAuditRecord.setCustomer(auditInfoList);
		return autoAuditRecord;
	}

	public static String getRemark(ValidType validType, String msg) {
		// TODO Anders Zhu : 将字符串重构
		switch (validType) {
		case URL:
			return String.format("URL [%s]", msg);
		case PHONE:
			return String.format("Phone [%s]", msg);
		case CUSTNAME:
			return String.format("CustName [%s]", msg);
		case DOMAIN:
			return String.format("Domain [%s]", msg);
		default:
			return StringUtils.EMPTY;
		}

	}

	// TODO Anders Zhu : 什么作用
	public static void mergeSaleData(List<SaleData> source, List<SaleData> dest) {
		if (CollectionUtils.isEmpty(dest))
			return;

		Set<Long> allCustIdSet = new HashSet<Long>();
		for (SaleData saleData : source) {
			allCustIdSet.add(saleData.getId());
		}
		for (SaleData saleData : dest) {
			if (allCustIdSet.add(saleData.getId())) {
				source.add(saleData);
			}
		}
	}
}
