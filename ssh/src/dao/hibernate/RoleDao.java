package dao.hibernate;

import model.Role;

import org.springframework.stereotype.Component;

import dao.hibernate.impl.BaseDao;

@Component
public class RoleDao extends BaseDao<Integer, Role>
{
}
