package com.baidu.rigel.unique.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.rigel.service.usercenter.bean.User;
import com.baidu.rigel.service.usercenter.service.UserMgr;

/**
 * 2009-8-17下午06:39:34
 * 
 * @author BAIDU.ECOM.RD - chenlong
 *         <p>
 *         说明：
 *         </p>
 * 
 */
public class NormalUserUtil {

	/**
	 * 说明：根据全局用户得到正常用户
	 * 
	 * @param allUser
	 * @param userMgr
	 * @return
	 * 
	 */
	public static List<User> getNormalUser(List<User> allUser, UserMgr userMgr) {
		List<User> normalUser = new ArrayList<User>();
		if (allUser == null || allUser.isEmpty()) {
			return normalUser;
		}
		List<Long> allUserId = new ArrayList<Long>();
		for (User u : allUser) {
			allUserId.add(u.getUcid());
		}
		List<Long> normalUserId = userMgr.getNormalUserIds(allUserId);
		if (normalUserId == null || normalUserId.isEmpty()) {
			return normalUser;
		}
		Map<String, String> normalMap = new HashMap<String, String>();
		for (Long id : normalUserId) {
			normalMap.put(id.toString(), id.toString());
		}
		for (User u : allUser) {
			String id = normalMap.get(u.getUcid().toString());
			if (id != null) {
				normalUser.add(u);
			}
		}
		return normalUser;
	}
}
