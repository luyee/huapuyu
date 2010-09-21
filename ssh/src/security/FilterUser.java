package security;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.xml.Role;
import model.xml.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.springframework.util.StringUtils;

import dao.hibernate.Dao;

public class FilterUser implements FactoryBean
{
	private String adminPage;
	private String userPage;
	private Dao<Integer, User> userDao;
	public static SessionFactory sessionFactory;

	public void setUserDao(Dao<Integer, User> userDao)
	{
		this.userDao = userDao;
	}

	public String getAdminPage()
	{
		return adminPage;
	}

	public void setAdminPage(String adminPage)
	{
		this.adminPage = adminPage;
	}

	public String getUserPage()
	{
		return userPage;
	}

	public void setUserPage(String userPage)
	{
		this.userPage = userPage;
	}

	@Override
	// 通过这个获取到自己的权限列表
	public Object getObject() throws Exception
	{

		DefaultFilterInvocationDefinitionSource definitionSource = null;
		UrlMatcher matcher = new AntUrlPathMatcher();// 可以扩这成多种访问形式
		LinkedHashMap<RequestKey, ConfigAttributeDefinition> urlMap = new LinkedHashMap<RequestKey, ConfigAttributeDefinition>();
		Map<String, String> map = getSourceRoleMap();
		Iterator<Map.Entry<String, String>> it = null;
		it = map.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<String, String> entry = it.next();
			urlMap.put(new RequestKey(entry.getKey()), new ConfigAttributeDefinition(StringUtils.commaDelimitedListToStringArray(entry.getValue())));
		}

		definitionSource = new DefaultFilterInvocationDefinitionSource(matcher, urlMap);

		return definitionSource;

	}

	private Map<String, String> getSourceRoleMap()
	{
		Map<String, String> sourceRoleMap = new LinkedHashMap<String, String>();
		sessionFactory = userDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		List<Role> record = session.createQuery("from Role").list();
		for (int i = 0; i < record.size(); i++)
		{
			Role role = record.get(i);
			switch (role.getId())
			{
				case 1:
					sourceRoleMap.put(adminPage, role.getName());
					break;
				case 2:
					sourceRoleMap.put(userPage, role.getName());
					break;
				default:
					break;
			}
		}

		return sourceRoleMap;
	}

	@Override
	public Class getObjectType()
	{
		return FilterInvocationDefinitionSource.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}

}
