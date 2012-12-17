package com.anders.ssh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ssh.bo.xml.Role;
import com.anders.ssh.dao.hibernate.RoleDao;
import com.anders.ssh.service.RoleService;

@Component
@Transactional
public class RoleServiceImpl implements RoleService {
	@Resource(name = "hibernateRoleDao")
	private RoleDao roleDao;

	@Override
	public void delete(Role role) {
		roleDao.delete(role);
	}

	@Override
	public void deleteById(Long id) {
		roleDao.deleteById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Role getById(Long id) {
		return roleDao.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Role> getAll() {
		return roleDao.getAll();
	}

	@Override
	public void save(Role role) {
		roleDao.save(role);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}
}
