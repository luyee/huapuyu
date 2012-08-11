package com.anders.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.anders.crm.bo.Role;
import com.anders.crm.dao.RoleDao;

//@Repository("roleDao")
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Long, Role> implements RoleDao {
}
