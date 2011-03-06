package dao.hibernate;

import model.User;

import org.springframework.stereotype.Component;

import dao.hibernate.impl.BaseDao;

@Component
public class UserDao extends BaseDao<Integer, User>
{
}
