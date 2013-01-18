package com.anders.ssh.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.FenBiao;

@Component("hibernateFenBiaoDao")
public class FenBiaoDao extends HibernateDao<Long, FenBiao> {
	public List<FenBiao> getAllData() {
		SQLQuery query = getSession().createSQLQuery("select * from fen_biao1");
		query.addEntity(FenBiao.class);
		return query.list();

		// List cats = sess.createSQLQuery( " select {cat.*} from cats cat " ).addEntity( " cat " , Cat. class ).list();

		// List cats = sess.createSQLQuery(
		// " select {cat.*}, {kitten.*} from cats cat, cats kitten where kitten.mother = cat.id " )
		// .addEntity( " cat " , Cat. class )
		// .addJoin( " kitten " , " cat.kittens " )
		// .list();

		// Double max = (Double) sess.createSQLQuery( " select max(cat.weight) as maxWeight from cats cat " )
		// .addScalar( " maxWeight " , Hibernate.DOUBLE);
		// .uniqueResult();

		// List cats = sess.createSQLQuery(
		// " select {cat.*}, {kitten.*} from cats cat, cats kitten where kitten.mother = cat.id " )
		// .setResultSetMapping( " catAndKitten " )
		// .list();
		//		
		// < sql - query name = " persons " >
		// < return alias = " person " class = " eg.Person " />
		// Select person.NAME AS {person.name},person.AGE AS {person.age},person.SEX AS {person.sex} FROM PERSON person Where person.NAME LIKE :namePattern
		// </ sql - query >List people = sess.getNamedQuery( " persons " ).setString( " namePattern " , namePattern)
		// .setMaxResults( 50 )
		// .list();

		// 使用hibernate3的createSQLQuery遇到的问题
		// 为了给访问加速，把DAO中的一些HQL的操作改成了SQL，其实最主要的原因是：操作的是多张表，返回的数据也来源于多个表的字段；
		// String sql = “select A.id ID, A.name NAME, B.salary SALARY from employee A , Salary B where.......”；
		// Query query =getSession().createSQLQuery(sql)
		// .setResultTransformer(Transformers.aliasToBean(ReturnEmployee.class));
		// 由于返回的ID, NAME, SALARY 非一个和表对应的一个BEAN，所以自己需要建立一个ReturnEmployee的BEAN，属性包括ID, NAME, SALARY；在mysql下调试，成功。
		// 但是在ORACLE环境下却报错：
		//
		// org.hibernate.PropertyNotFoundException: Could not find setter for ID on class com.ReturnEmployee
		//
		// 经过几个小时的查错，调试，没有发现问题的所在，只能摆脱GOOGLE了，最后在国外的一个论坛上找到了答案：
		//
		// this is actually a limitation of some databases which return alias all uppercase instead of using the casing you actually specified.
		//
		// until then use .addScalar(..) to workaround it.
		//
		// 原来是Hibernate对ORALCE的支持有BUG，所以修改代码为：
		// Query query = getSession().createSQLQuery(sql).addScalar("ID")
		// .addScalar("NAME").addScalar("SALARY");
		// 就可以了，需要注意的是
		// List employeeData = query.list();
		// 返回的employeeData 中的数据是object[]，这样取值：
		// List employeeBean = new ArrayList();
		// for (int i = 0; i < employeeData.size(); i++) {
		// Employee employee = new Employee();//把"裸"数据组装到自己的employee类
		//
		// Object[] object = (Object[]) employeeData.get(i);
		// employee.setId(object[0].toString());
		// employee.setName(object[1].toString());
		// employee.setOrgType(object[2].toString());
		//
		// employeeBean.add(employee);
		// }
	}
}
