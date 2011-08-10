/**
 * 
 */
package com.baidu.rigel.unique.common;

import com.baidu.rigel.platform.vo.ErrorInfo;
import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.service.usercenter.service.UserMgr;
import com.baidu.rigel.unique.uc.impl.MockUserCenterHelper;

/**
 * @author liuzhaobing
 * @version 1.0.0 处理部分共通的信息：
 */
public class BaseActionSupport extends com.opensymphony.xwork2.ActionSupport {
	/** 静态资源服务器 */
	// protected String staticRoot = SysProperty.STATIC_SOURCE_SERVER;
	// TODO Anders Zhu : 重构
	protected String staticRoot = "";
	/** 统一返回给fe同学的message */
	protected String reason;
	/** 登陆相关信息操作类 */
	// TODO Anders Zhu : mock
	// protected UserCenterHelper ucHelper = new UserCenterHelper();
	protected MockUserCenterHelper ucHelper = new MockUserCenterHelper();
	/** 账号中心mgr */
	protected UserMgr userMgr;

	protected Page page;

	protected ErrorInfo errorInfo;

	public BaseActionSupport() {
		super();
	}

	/**
	 * @return the errorInfo
	 */
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	/**
	 * @param errorInfo
	 *            the errorInfo to set
	 */
	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getStaticRoot() {
		return staticRoot;
	}

	public void setStaticRoot(String staticRoot) {
		this.staticRoot = staticRoot;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public MockUserCenterHelper getUcHelper() {
		return ucHelper;
	}

	public void setUcHelper(MockUserCenterHelper ucHelper) {
		this.ucHelper = ucHelper;
	}

	public UserMgr getUserMgr() {
		return userMgr;
	}

	public void setUserMgr(UserMgr userMgr) {
		this.userMgr = userMgr;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
