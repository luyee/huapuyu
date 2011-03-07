package dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import security.Page;
import security.PropertyFilter;
import security.ReflectionUtils;
import security.PropertyFilter.MatchType;
import dao.interf.Dao;

public abstract class BaseDao<PK extends Serializable, T> extends HibernateDaoSupport implements Dao<PK, T>
{
	// 增加setSessionFactoryMocker方法，避免在XML文件中给DAO方法注入SessionFactory。
	@Resource
	public void setSessionFactoryMocker(SessionFactory sessionFactory)
	{
		super.setSessionFactory(sessionFactory);
	}

	private Class<T> entityClass;

	public BaseDao()
	{
		entityClass = getSuperClassGenricType();
	}

	public Class<T> getSuperClassGenricType()
	{
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[1];
	}

	@Override
	public void delete(T entity)
	{
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteById(PK id)
	{
		getHibernateTemplate().delete(this.getById(id));
	}

	@Override
	public void save(T entity)
	{
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity)
	{
		getHibernateTemplate().update(entity);
	}

	@Override
	public T getById(PK id)
	{
		return getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> getAll()
	{
		return getHibernateTemplate().loadAll(entityClass);
	}

	public Query createQuery(final String queryString, final Object... values)
	{
		Query query = getSession().createQuery(queryString);
		if (values != null)
		{
			for (int i = 0; i < values.length; i++)
			{
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public <X> X findUnique(final String hql, final Object... values)
	{
		return (X) createQuery(hql, values).uniqueResult();
	}

	public <X> X findUnique(final String hql, final Map<String, ?> values)
	{
		return (X) createQuery(hql, values).uniqueResult();
	}

	public <X> List<X> find(final String hql, final Object... values)
	{
		return createQuery(hql, values).list();
	}

	public List<T> getAll(String orderByProperty, boolean isAsc)
	{
		Criteria c = createCriteria();
		if (isAsc)
		{
			c.addOrder(Order.asc(orderByProperty));
		}
		else
		{
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue)
	{
		if (newValue == null || newValue.equals(oldValue))
		{
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values)
	{
		return createQuery(hql, values).list();
	}

	public T findUniqueBy(final String propertyName, final Object value)
	{
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	// -- 分页查询函数 --//

	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page)
	{
		return findPage(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数. 注意不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values)
	{

		Query q = createQuery(hql, values);

		if (page.isAutoCount())
		{
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数. 注意不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values)
	{

		Query q = createQuery(hql, values);

		if (page.isAutoCount())
		{
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final Criterion... criterions)
	{

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount())
		{
			long totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameterToCriteria(c, page);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	public Criteria createCriteria(final Criterion... criterions)
	{
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions)
		{
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final Page<T> page)
	{
		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c, final Page<T> page)
	{
		// hibernate的firstResult的序号从0开始
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted())
		{
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			for (int i = 0; i < orderByArray.length; i++)
			{
				if (Page.ASC.equals(orderArray[i]))
				{
					c.addOrder(Order.asc(orderByArray[i]));
				}
				else
				{
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values)
	{
		String countHql = prepareCountHql(hql);

		try
		{
			Long count = findUnique(countHql, values);
			return count;
		}
		catch (Exception e)
		{
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values)
	{
		String countHql = prepareCountHql(hql);

		try
		{
			Long count = findUnique(countHql, values);
			return count;
		}
		catch (Exception e)
		{
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	private String prepareCountHql(String orgHql)
	{
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings("unchecked")
	protected long countCriteriaResult(final Criteria c)
	{
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try
		{
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		// 执行Count查询
		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null)
		{
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null)
		{
			c.setResultTransformer(transformer);
		}
		try
		{
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		return totalCount;
	}

	// -- 属性过滤条件(PropertyFilter)查询函数 --//

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType
	 *            匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType)
	{
		Criterion criterion = buildCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters)
	{
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return find(criterions);
	}

	public List<T> find(final Criterion... criterions)
	{
		return createCriteria(criterions).list();
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters)
	{
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		return findPage(page, criterions);
	}

	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildCriterion(final String propertyName, final Object propertyValue, final MatchType matchType)
	{
		Criterion criterion = null;
		// 根据MatchType构造criterion
		switch (matchType)
		{
		case EQ:
			criterion = Restrictions.eq(propertyName, propertyValue);
			break;
		case LIKE:
			criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
			break;

		case LE:
			criterion = Restrictions.le(propertyName, propertyValue);
			break;
		case LT:
			criterion = Restrictions.lt(propertyName, propertyValue);
			break;
		case GE:
			criterion = Restrictions.ge(propertyName, propertyValue);
			break;
		case GT:
			criterion = Restrictions.gt(propertyName, propertyValue);
		}
		return criterion;
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildCriterionByPropertyFilter(final List<PropertyFilter> filters)
	{
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters)
		{
			if (!filter.hasMultiProperties())
			{ // 只有一个属性需要比较的情况.
				Criterion criterion = buildCriterion(filter.getPropertyName(), filter.getMatchValue(), filter.getMatchType());
				criterionList.add(criterion);
			}
			else
			{// 包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames())
				{
					Criterion criterion = buildCriterion(param, filter.getMatchValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}
}
