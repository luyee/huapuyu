package com.baidu.rigel.unique.uc.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.baidu.rigel.service.session.bo.Session;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.service.usercenter.bean.UserInfo;
import com.baidu.rigel.service.usercenter.tool.UserCenterHelper;

public class MockUserCenterHelper extends UserCenterHelper {

	@Override
	public List<Position> getAllPos() {
		return new ArrayList<Position>(0);
	}

	@Override
	public List<Position> getAllPosList() {
		return new ArrayList<Position>(0);
	}

	@Override
	public Set<String> getAuthTags() {
		return new HashSet<String>(0);
	}

	@Override
	public Position getCurrentPos() {
		Position position = new Position();
		position.setPosid(11L);
		return position;
	}

	@Override
	public Position getDefaultPos() {
		return new Position();
	}

	@Override
	public Session getSession() {
		return null;
	}

	@Override
	public User getUser() {
		User user = new User();
		user.setUcid(0L);
		return user;
	}

	@Override
	public UserInfo getUserInfo() {
		return new UserInfo();
	}

	@Override
	public boolean hasAuth(String authTag) {
		return true;
	}

	@Override
	public boolean hasRole(String roleTag) {
		return true;
	}

	@Override
	public void setAllPosList(List<Position> poses) {
	}

	@Override
	public void setSession(Session session) {
	}

}
