package com.baidu.rigel.unique.uc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.rigel.service.usercenter.bean.PosPath;
import com.baidu.rigel.service.usercenter.bean.Position;
import com.baidu.rigel.service.usercenter.bean.PositionPath;
import com.baidu.rigel.service.usercenter.bean.RoleInfo;
import com.baidu.rigel.service.usercenter.bean.UcidsList;
import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.service.usercenter.bean.UserInfo;
import com.baidu.rigel.service.usercenter.bean.UserPos;
import com.baidu.rigel.service.usercenter.exception.UserCenterErrorException;
import com.baidu.rigel.service.usercenter.service.UserMgr;

@Service("userMgr")
public class UserMgrImpl implements UserMgr {

	public Map<Long, User> getUserMap(List<Long> ucids) throws UserCenterErrorException {
		return new HashMap<Long, User>();
	}

	public Map<Long, Position> getPositionMapByIds(List<Long> posids) throws UserCenterErrorException {
		return new HashMap<Long, Position>();
	}

	public List<User> getUserList(List<Long> ucids) throws UserCenterErrorException {
		return new ArrayList<User>(0);
	}

	public User getUserById(Long ucid) throws UserCenterErrorException {
		return null;
	}

	public List<Position> getUnitPos(Long posid) throws UserCenterErrorException {
		return new ArrayList<Position>(0);
	}

	public Long[] getUnitPosId(Long posid) throws UserCenterErrorException {
		return new Long[0];
	}

	public List<Long> getNormalUserIds(List<Long> ucids) {
		return new ArrayList<Long>(0);
	}

	public List<Long> getALLDefaultUserIdsByPos(Long arg0, int arg1) throws UserCenterErrorException {
		return null;
	}

	public UserPos getAllPosById(Long arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public List<Position> getAllPositionByIds(List<Long> arg0) throws UserCenterErrorException {
		return null;
	}

	public List<Long> getAllUserIdsByPos(Long arg0, int arg1) throws UserCenterErrorException {
		return null;
	}

	public List<Long> getAllUserIdsByPosAndRole(Long arg0, String arg1) throws UserCenterErrorException {
		return new ArrayList<Long>(0);
	}

	public UcidsList getAllUserIdsByPosAndRole(Long arg0, String arg1, int arg2, int arg3) throws UserCenterErrorException {
		return null;
	}

	public Position getDefaultPosById(Long arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public List<User> getManagerById(Long arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public Position getParentPos(Long arg0) throws UserCenterErrorException {
		return null;
	}

	public List<PositionPath> getPosPathByPosid(List<Long> arg0, int arg1) throws UserCenterErrorException {
		return null;
	}

	public List<PosPath> getPosPathByUser(List<Long> arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public Map<Long, PositionPath> getPosPathMapByPosid(List<Long> arg0, int arg1) throws UserCenterErrorException {
		return null;
	}

	public Map<Long, PosPath> getPosPathMapByUser(List<Long> arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public Position getPositionById(Long arg0) throws UserCenterErrorException {
		return null;
	}

	public List<Position> getPositionByIds(List<Long> arg0) throws UserCenterErrorException {
		return null;
	}

	public Position getPositionByTag(String arg0) throws UserCenterErrorException {
		return null;
	}

	public RoleInfo getRoleInfoByTag(String arg0) throws UserCenterErrorException {
		return null;
	}

	public List<Position> getSubTreeByPos(Long arg0) throws UserCenterErrorException {
		return null;
	}

	public List<Position> getUnitPosByTree(Long arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public List<Position> getUnitPosOfTree(String arg0) throws UserCenterErrorException {
		return null;
	}

	public User getUserByName(String arg0) throws UserCenterErrorException {
		return null;
	}

	public Long getUserIdByNameWithMgrPosid(Long arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public List<Long> getUserIdsByPosAndRole(Long arg0, String arg1) throws UserCenterErrorException {
		return null;
	}

	public UcidsList getUserIdsByPosAndRole(Long arg0, String arg1, int arg2, int arg3) throws UserCenterErrorException {
		return null;
	}

	public UserInfo userLogin(Long arg0, Long arg1, List<String> arg2) throws UserCenterErrorException {
		return null;
	}
}
