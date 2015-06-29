package com.vip.mybatis.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果对象������
 * 
 * @author Mazhy.Keng
 * @since 1.0.0.0
 */
public class PagineBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8265919959641598206L;

	/**
	 * 当前页码
	 */
	private Long curPageNum;
	/**
	 * 上一页
	 */
	private Long prePage;
	/**
	 * 下一页
	 */
	private Long nextPage;
	/**
	 * 总页数
	 */
	private Long totalPages;
	/**
	 * 是否首页
	 */
	private Boolean isFirstPage;
	/**
	 * 是否页末
	 */
	private Boolean isLastPage;
	/**
	 * 每页显示行数
	 */
	private Long pageSize;
	/**
	 * 本页开始序号
	 */
	private Long startSerial;
	/**
	 * 当页数据列表
	 */
	private List dataList;

	/**
	 * 总记录数
	 */
	private Long totalRecNum;

	/**
	 * 排序字段名称，多个使用','分隔
	 */
	private String sortBy;

	/**
	 * 排序字段方式，为"asc"或"desc"，多个使用','分隔，与sortBy内容对应
	 */
	private String orderBy;

	public String getSortBy() {
		return sortBy;
	}

	/**
	 * 排序字段名称，多个使用','分隔
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 排序字段方式，为"asc"或"desc"，多个使用','分隔，与sortBy内容对应
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * default constructor
	 */
	public PagineBean() {

	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public Boolean getIsFirstPage() {
		return isFirstPage;
	}

	public void setIsFirstPage(Boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public Boolean getIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(Boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public Long getPrePage() {
		return prePage;
	}

	public void setPrePage(Long prePage) {
		this.prePage = prePage;
	}

	public Long getNextPage() {
		return nextPage;
	}

	public void setNextPage(Long nextPage) {
		this.nextPage = nextPage;
	}

	public Long getStartSerial() {
		return startSerial;
	}

	public void setStartSerial(Long startSerial) {
		this.startSerial = startSerial;
	}

	/**
	 * @return the curPageNum
	 */
	public Long getCurPageNum() {
		return curPageNum;
	}

	/**
	 * @param curPageNum
	 *            the curPageNum to set
	 */
	public void setCurPageNum(Long curPageNum) {
		this.curPageNum = curPageNum;
	}

	/**
	 * @return the pageSize
	 */
	public Long getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the totalRecNum
	 */
	public Long getTotalRecNum() {
		return totalRecNum;
	}

	/**
	 * @param totalRecNum
	 *            the totalRecNum to set
	 */
	public void setTotalRecNum(Long totalRecNum) {
		this.totalRecNum = totalRecNum;
	}

	public Long getCur_page_num() {
		return getCurPageNum();
	}

	public void setCur_page_num(Long cur_page_num) {
		setCurPageNum(cur_page_num);
	}

	public Long getPage_size() {
		return getPageSize();
	}

	public void setPage_size(Long page_size) {
		setPageSize(page_size);
	}

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public Long getTotal_page_num() {
		return getTotalRecNum();
	}

	/**
	 * 设置总记录数
	 * 
	 * @param total_record_num
	 */
	public void setTotal_page_num(Long total_record_num) {
		setTotalRecNum(total_record_num);
	}
}
