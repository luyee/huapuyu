package com.anders.vote.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.anders.vote.vo.UserVO;

public class SecurityAction extends BaseAction /* implements ModelDriven<UserVO> */{

	private static final long serialVersionUID = -3013656302209804787L;

	private UserVO userVO = new UserVO();
	private String userName;
	private String password;

	public String login() {
		return SUCCESS;
	}

	public String loginx() {
		System.out.println(userName);
		System.out.println(password);

		UsernamePasswordToken token = new UsernamePasswordToken(userVO.getUserName(), userVO.getPassword(), false);
		// try {
		SecurityUtils.getSubject().login(token);
		// }
		// catch (AuthenticationException e) {
		// System.out.println(e.getMessage());
		// }

		return SUCCESS;
	}

	// getter and setter

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	/*
	 * @Override public UserVO getModel() { return userVO; }
	 */

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
