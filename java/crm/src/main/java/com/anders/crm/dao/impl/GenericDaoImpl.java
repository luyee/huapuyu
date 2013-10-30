package com.anders.crm.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.anders.crm.dao.GenericDao;
import com.anders.crm.utils.Reflections;

/**
 * Generic Data Access Object Implement
 * 
 * @author Anders Zhu
 * 
 */
// public abstract class GenericDaoImpl<PK extends Serializable, T> extends HibernateDaoSupport implements GenericDao<PK, T> {
public abstract class GenericDaoImpl<PK extends Serializable, T> implements GenericDao<PK, T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// @Resource(name = "sessionFactory")
	// public void setSuperSessionFactory(SessionFactory sessionFactory) {
	// super.setSessionFactory(sessionFactory);
	// }

	@Autowired
	private SessionFactory sessionFactory;

	 public SessionFactory getSessionFactory() {
	 return sessionFactory;
	 }
	
	// @Resource(name = "sessionFactory")
	// public void setSessionFactory(SessionFactory sessionFactory) {
	// this.sessionFactory = sessionFactory;
	// }

	public Session getSession() {
		// 必须开启事务，否则获取不到Session
		return sessionFactory.getCurrentSession();
	}

	private Class<T> entityClass;

	public GenericDaoImpl() {
		entityClass = Reflections.getSuperClassGenricType(getClass());
	}

	// TODO Anders Zhu : 为什么删除不掉
	public void delete(final T entity) {
		Assert.notNull(entity, "entity is null");
		// getHibernateTemplate().delete(entity);
		getSession().delete(entity);
	}

	// TODO Anders Zhu : 观察此方法是否优化,如何加入版本号
	public void deleteById(final PK id) {
		Assert.notNull(id, "id is null");
		// getHibernateTemplate().delete(getHibernateTemplate().load(entityClass, id));
		// getSession().delete(getSession().load(entityClass, id));
		String hql = String.format("delete from %s entity where entity.id = %d", entityClass.getSimpleName(), id);
		getSession().createQuery(hql).executeUpdate();
	}

	// TODO Anders Zhu : 如何加入版本号
	public void disabledById(final PK id) {
		Assert.notNull(id, "id is null");
		String hql = String.format("update %s entity set entity.enabled = false, entity.version = entity.version + 1 where entity.id = %d", entityClass.getSimpleName(), id);
		getSession().createQuery(hql).executeUpdate();
	}

	public void save(final T entity) {
		Assert.notNull(entity, "entity is null");
		// getHibernateTemplate().save(entity);
		getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is null");
		// getHibernateTemplate().update(entity);
		getSession().update(entity);
	}

	public void saveOrUpdate(T entity) {
		Assert.notNull(entity, "entity is null");
		// getHibernateTemplate().saveOrUpdate(entity);
		getSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public T getById(final PK id) {
		Assert.notNull(id, "id is null");
		// return getHibernateTemplate().get(entityClass, id);
		// TODO Anders Zhu : 研究下getSession().load(theClass, id, lockOptions)，参考tcom
		return (T) getSession().get(entityClass, id);
	}

	public List<T> getByIds(final Collection<PK> ids) {
		Assert.notEmpty(ids, "ids is empty");
		return findList(Restrictions.in(getIdName(), ids));
	}

	protected String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	public List<T> getAll() {
		// return getHibernateTemplate().loadAll(entityClass);
		return findList();
	}

	/**
	 * 按属性查找对象列表，匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName is empty");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return findList(criterion);
	}

	public List<T> findBy(final String propertyName, final Object value, final String selectPropertyName) {
		Assert.hasText(propertyName, "propertyName is empty");
		Criterion criterion = Restrictions.eq(propertyName, value);
		ProjectionList projectionList = Projections.projectionList().add(Projections.property(selectPropertyName));
		return findList(projectionList, criterion);
	}

	/**
	 * 按属性查找唯一对象，匹配方式为相等.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName is empty");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString is empty");
		Query query = getSession().createQuery(queryString);
		// if (values != null)
		if (ArrayUtils.isNotEmpty(values))
			for (int i = 0; i < values.length; i++)
				query.setParameter(i, values[i]);
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		Assert.hasText(queryString, "queryString is empty");
		Query query = getSession().createQuery(queryString);
		// if (values != null) {
		if (MapUtils.isNotEmpty(values)) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findList(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findList(final ProjectionList projectionList, final Criterion... criterions) {
		return createCriteria(criterions).setProjection(projectionList).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria，与find()函数可进行更加灵活的操作
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (ArrayUtils.isNotEmpty(criterions))
			for (Criterion c : criterions)
				criteria.add(c);
		return criteria;
	}

	/**
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行: Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合. Hibernate.initialize (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	// TODO Anders Zhu : 这个方法研究下
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue))
			return true;
		Object object = findUniqueBy(propertyName, newValue);
		return object == null;
	}
}
