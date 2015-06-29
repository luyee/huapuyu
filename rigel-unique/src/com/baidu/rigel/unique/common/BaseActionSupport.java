/**
 * 
 */
package com.baidu.rigel.unique.common;

import com.baidu.rigel.platform.vo.ErrorInfo;
import com.baidu.rigel.platform.vo.Page;
import com.baidu.rigel.service.usercenter.service.UserMgr;
import com.baidu.rigel.service.usercenter.tool.UserCenterHelper;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 自定义Action基类
 * 
 * @author Anders Zhu
 * 
 */
public class BaseActionSupport extends ActionSupport {
	private static final long serialVersionUID = 3778461156131013777L;
	/** 静态资源服务器 */
	// protected String staticRoot = SysProperty.STATIC_SOURCE_SERVER;
	/** 统一返回给fe同学的message */
	protected String reason;
	/** 登陆相关信息操作类 */
	protected UserCenterHelper userCenterHelper = new UserCenterHelper();
	/** 账号中心类 */
	protected UserMgr userMgr;
	protected Page page;
	protected ErrorInfo errorInfo;

	public BaseActionSupport() {
		super();
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public UserCenterHelper getUserCenterHelper() {
		return userCenterHelper;
	}

	public void setUserCenterHelper(UserCenterHelper userCenterHelper) {
		this.userCenterHelper = userCenterHelper;
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

	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

}
