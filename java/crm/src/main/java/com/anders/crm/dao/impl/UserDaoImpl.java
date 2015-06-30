package com.anders.crm.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.anders.crm.bo.User;
import com.anders.crm.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<Long, User> implements UserDao {
	public String getNameByUsername(String username) {
		return findUnique("select user.name from User user where user.username = ?", new Object[] { username });
	}

	public boolean isExistByUsername(String username) {
		Long count = findUnique("select count(*) from User user where user.username = ?", new Object[] { username });
		if (count != null && count.equals(1L)) {
			return true;
		}
		return false;
	}

	public boolean isExistByEmail(String email) {
		Long count = findUnique("select count(*) from User user where user.email = ?", new Object[] { email });
		if (count != null && count.equals(1L)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<String> getUsernameById(Long id) {
		SQLQuery query = getSession().createSQLQuery("select u.user_name user_name from tb_user u where u.id = :id");
		query.setParameter("id", id);
		query.addScalar("user_name", StringType.INSTANCE);
		return query.list();
	}
}
