package com.baidu.rigel.unique.tinyse;

import java.util.List;

import com.baidu.rigel.service.tinyse.data.SaleData;
import com.baidu.rigel.service.tinyse.data.ShifenData;

public interface TinyseMgr {
	public static final double QUERY_THRESHOLD = 1.0;

	public static final int MAX_NUM_LIMIT = 1000;

	/**
	 * 增加销售客户资料
	 * 
	 * @param custId
	 * @param companyname
	 */
	public boolean addSaleData(Long custId, String companyname);

	/**
	 * 修改销售客户资料
	 * 
	 * @param custId
	 * @param companyname
	 */
	public boolean modSaleData(Long custId, String companyname);

	/**
	 * 删除销售客户资料
	 * 
	 * @param ids
	 */
	public boolean delSaleData(List<Long> ids);

	/**
	 * 查询销售客户资料,最大返回1000
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<SaleData> querySaleData(String query);

	/**
	 * 查询销售客户资料
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<SaleData> querySaleData(String query, int limit);

	/**
	 * 查询销售客户资料
	 * 
	 * @param query
	 * @param pageNum
	 * @param limit
	 * @return
	 * @author liuzhaobing@20090601
	 */
	public List<SaleData> querySaleData(String query, int pageNum, int limit);

	/**
	 * 查询shifen客户资料
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<ShifenData> queryShifenData(String query, int limit);

	/**
	 * 查询销售客户资料(支持重复率阀值)
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<SaleData> querySaleDataWithHold(String query, int limit);

	/**
	 * 查询shifen客户资料(支持重复率阀值)
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<ShifenData> queryShifenDataWithHold(String query, int limit);

	/**
	 * 按照基本词查询销售客户资料
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<SaleData> querySaleDataWithWordPhrase(String query, int limit);

	public List<SaleData> querySaleDataWithWordPhrase(String query, int pageNum, int limit);

	/**
	 * 按照基本词查询shifen客户资料
	 * 
	 * @param query
	 * @param limit
	 * @return
	 */
	public List<ShifenData> queryShifenDataWithWordPhrase(String query, int limit);

	public List<SaleData> querySeasonCustDataWithHold(String query, int limit);

	public String removeNoneCoreWord(String query);

	public String removeAreaWord(String query, Long posId);

	public boolean addSeasonCustData(Long seasonCustId, String companyname);

	public boolean delSeasonCustData(List<Long> ids);

}
