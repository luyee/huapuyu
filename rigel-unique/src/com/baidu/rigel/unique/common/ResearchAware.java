package com.baidu.rigel.unique.common;

/**
 * 重设查询条件
 * 从详情页面返回到列表页面需要保存查询条件和查询分页数的时候需要实现该接口
 * @author liuzhaobing
 * @version 1.0.0
 *
 */
public interface ResearchAware {

	/**
	 * 设置查询条件的id到session
	 * @param id
	 */
	public void setQueryId(String id);
	
	/**
	 * 取得查询条件在session中的id
	 * @param id
	 */
	public String getQueryId();
	
	/**
	 * 取得页面名称
	 */
	public String getPageName();
}
