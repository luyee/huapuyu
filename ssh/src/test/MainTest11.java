package test;

import java.sql.SQLException;

import model.test.Tb_user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibatis.sqlmap.client.SqlMapClient;

import dao.hibernate.Tb_userDao;

public class MainTest11
{
	public void Test1()
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");

		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Tb_user model = new Tb_user();
		model.setName("zhuzhen");
		model.setPwd("123");

		session.save(model);
		tx.commit();
	}

	public void Test2()
	{
		ApplicationContext actx = new ClassPathXmlApplicationContext("applicationContext.xml");
		SqlMapClient sf = (SqlMapClient) actx.getBean("sqlMapClient");
		try
		{
			// sf.startTransaction();
			Tb_user model = new Tb_user();

			model.setId(98);
			model.setName("guolili");
			model.setPwd("123456");

			// sf.insert("Tb_userInsert", model);

			model = (Tb_user) sf.queryForObject("Tb_userSelectById", 97);
			System.out.println(model.getName());
			System.out.println(model.getPwd());

			// sf.commitTransaction();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

	public void test3()
	{
		Tb_user model = new Tb_user();
		model.setId(90);
		model.setName("hello");
		model.setPwd("123");

		Tb_userDao dao = new Tb_userDao();
		dao.save(model);
	}
}
