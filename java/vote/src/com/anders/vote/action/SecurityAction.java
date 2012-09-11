package com.anders.vote.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.anders.vote.vo.UserVO;

public class SecurityAction extends BaseAction {

	private static final long serialVersionUID = -3013656302209804787L;

	private UserVO userVO = new UserVO();

	public String login() {

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

}
