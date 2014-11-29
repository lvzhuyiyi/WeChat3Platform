package web.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 
 * 类描述： DAO层泛型基类
 * 
 * 张代浩
 * 
 * @date： 日期：2012-12-7 时间：上午10:16:48
 * @param
 * @param <PK>
 * @version 1.0
 */
/*
 * annotation是可以继承的，所以必须而且一定要在基类中添加@Transactional,否则即使子类加了，对应父类方法也没用
 */
@SuppressWarnings("hiding")
@Transactional
public abstract class GenericBaseCommonDao {
	/**
	 * 初始化Log4j的一个实例
	 */
	private static final Logger logger = Logger
			.getLogger(GenericBaseCommonDao.class);
	/**
	 * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
	 * **/
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 获得该类的属性和类型
	 * 
	 * @param entityName
	 *            注解的实体类
	 */
	private void getProperty(Class entityName) {
		ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
		String[] str = cm.getPropertyNames(); // 获得该类所有的属性名称
		for (int i = 0; i < str.length; i++) {
			String property = str[i];
			String type = cm.getPropertyType(property).getName(); // 获得该名称的类型

		}
	}

	/**
	 * 获取所有数据表
	 * 
	 * @return
	 */
	public Integer getAllDbTableSize() {
		SessionFactory factory = getSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		return metaMap.size();
	}

	/**
	 * 根据实体名字获取唯一记录
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public Object findUniqueByProperty(Class entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.uniqueResult();
	}

	/**
	 * 按属性查找对象列表.
	 */
	public List findByProperty(Class entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.list();
	}

	/**
	 * 根据传入的实体持久化对象
	 */
	public Serializable save(Object entity) {
		try {
			Serializable id = getSession().save(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("保存实体成功," + entity.getClass().getName());
			}
			return id;
		} catch (RuntimeException e) {
			logger.error("保存实体异常", e);
			throw e;
		}

	}

	/**
	 * 批量保存数据
	 * 
	 * @param
	 * @param entitys
	 *            要持久化的临时实体对象集合
	 */
	public void batchSave(List entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			getSession().save(entitys.get(i));
			if (i % 20 == 0) {
				// 20个对象后才清理缓存，写入数据库
				getSession().flush();
				getSession().clear();
			}
		}
		// 最后清理一下----防止大于20小于40的不保存
		getSession().flush();
		getSession().clear();
	}

	/**
	 * 根据传入的实体添加或更新对象
	 * 
	 * @param
	 * 
	 * @param entity
	 */

	public void saveOrUpdate(Object entity) {
		try {
			getSession().saveOrUpdate(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("添加或更新成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
	}

	/**
	 * 根据传入的实体删除对象
	 */
	public void delete(Object entity) {
		try {
			getSession().delete(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("删除成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("删除异常", e);
			throw e;
		}
	}

	/**
	 * 根据主键删除指定的实体
	 * 
	 * @param
	 * @param pojo
	 */
	public void deleteEntityById(Class entityName, Serializable id) {
		delete(get(entityName, id));
		getSession().flush();
	}

	/**
	 * 删除全部的实体
	 * 
	 * @param
	 * 
	 * @param entitys
	 */
	public void deleteAllEntitie(Collection entitys) {
		for (Object entity : entitys) {
			getSession().delete(entity);
			getSession().flush();
		}
	}

	/**
	 * 根据Id获取对象。
	 */
	public Object get(Class entityClass, final Serializable id) {

		return getSession().get(entityClass, id);

	}

	/**
	 * 根据主键获取实体并加锁。
	 * 
	 * @param
	 * @param entityName
	 * @param id
	 * @param lock
	 * @return
	 */
	public Object getEntity(Class entityName, Serializable id) {

		Object t = getSession().get(entityName, id);
		if (t != null) {
			getSession().flush();
		}
		return t;
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param
	 * @param pojo
	 */
	public void updateEntity(Object pojo) {
		getSession().update(pojo);
		getSession().flush();
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param
	 * @param pojo
	 */
	public void updateEntity(String className, Object id) {
		getSession().update(className, id);
		getSession().flush();
	}

	/**
	 * 根据主键更新实体
	 */
	public void updateEntityById(Class entityName, Serializable id) {
		updateEntity(get(entityName, id));
	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param
	 * @param query
	 * @return
	 */
	public List findByQueryString(final String query) {

		Query queryObject = getSession().createQuery(query);
		List list = queryObject.list();
		if (list.size() > 0) {
			getSession().flush();
		}
		return list;

	}

	/**
	 * 通过hql 查询语句查找HashMap对象
	 * 
	 * @param
	 * @param query
	 * @return
	 */
	public Map<Object, Object> getHashMapbyQuery(String hql) {

		Query query = getSession().createQuery(hql);
		List list = query.list();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] tm = (Object[]) iterator.next();
			map.put(tm[0].toString(), tm[1].toString());
		}
		return map;

	}

	/**
	 * 通过sql更新记录
	 * 
	 * @param
	 * @param query
	 * @return
	 */
	public int updateBySqlString(final String query) {

		Query querys = getSession().createSQLQuery(query);
		return querys.executeUpdate();
	}

	/**
	 * 通过sql查询语句查找对象
	 * 
	 * @param
	 * @param query
	 * @return
	 */
	public List findListbySql(final String sql) {
		Query querys = getSession().createSQLQuery(sql);
		return querys.list();
	}

	/**
	 * 创建Criteria对象，有排序功能。
	 * 
	 * @param
	 * @param entityClass
	 * @param orderBy
	 * @param isAsc
	 * @param criterions
	 * @return
	 */
	private Criteria createCriteria(Class entityClass, boolean isAsc,
			Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc("asc"));
		} else {
			criteria.addOrder(Order.desc("desc"));
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象带属性比较
	 * 
	 * @param
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private Criteria createCriteria(Class entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public List loadAll(final Class entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
	}

	/**
	 * 创建单一Criteria对象
	 * 
	 * @param
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private Criteria createCriteria(Class entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}

	/**
	 * 根据属性名和属性值查询. 有排序
	 * 
	 * @param
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List findByPropertyisOrder(Class entityClass, String propertyName,
			Object value, boolean isAsc) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}

	public List findByPropertyByFieldOrder(Class entityClass,
			String propertyName, Object value, String orderfield) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.addOrder(Order.desc(orderfield)).list();
	}

	public List findByPropertyByFieldAscOrder(Class entityClass,
			String propertyName, Object value, String orderfield) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.addOrder(Order.asc(orderfield)).list();
	}

	/**
	 * 根据属性名和属性值 查询 且要求对象唯一.
	 * 
	 * @return 符合条件的唯一对象.
	 */
	public Object findUniqueBy(Class entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.uniqueResult();
	}

	/**
	 * 根据查询条件与参数列表创建Query对象
	 * 
	 * @param session
	 *            Hibernate会话
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            参数列表
	 * @return Query对象
	 */
	public Query createQuery(Session session, String hql, Object... objects) {
		Query query = session.createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query;
	}

	/**
	 * 批量插入实体
	 * 
	 * @param clas
	 * @param values
	 * @return
	 */
	public int batchInsertsEntitie(List entityList) {
		int num = 0;
		for (int i = 0; i < entityList.size(); i++) {
			save(entityList.get(i));
			num++;
		}
		return num;
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param
	 * @param hql
	 * @param size
	 * @return
	 */
	/**
	 * 使用占位符的方式填充值 请注意：like对应的值格式："%"+username+"%" Hibernate Query
	 * 
	 * @param hibernateTemplate
	 * @param hql
	 * @param valus
	 *            占位符号?对应的值，顺序必须一一对应 可以为空对象数组，但是不能为null
	 * @return 2008-07-19 add by liuyang
	 */
	public List executeQuery(final String hql, final Object[] values) {
		Query query = getSession().createQuery(hql);
		// query.setCacheable(true);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		return query.list();

	}

	/**
	 * 根据实体模版查找
	 * 
	 * @param entityName
	 * @param exampleEntity
	 * @return
	 */

	public List findByExample(final String entityName,
			final Object exampleEntity) {
		Assert.notNull(exampleEntity, "Example entity must not be null");
		Criteria executableCriteria = (entityName != null ? getSession()
				.createCriteria(entityName) : getSession().createCriteria(
				exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		return executableCriteria.list();
	}

	/**
	 * 调用存储过程
	 */
	public void callableStatementByName(String proc) {
	}

	/**
	 * 查询指定实体的总记录数
	 * 
	 * @param clazz
	 * @return
	 */
	public int getCount(Class clazz) {

		int count = DataAccessUtils.intResult(getSession().createQuery(
				"select count(*) from " + clazz.getName()).list());
		return count;
	}

	/**
	 * 使用指定的检索标准检索数据并分页返回数据For JDBC
	 */

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param
	 * @param query
	 * @return
	 */
	public List findHql(String hql, Object... param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	/**
	 * 执行HQL语句操作更新
	 * 
	 * @param hql
	 * @return
	 */
	public Integer executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
	}

	public List pageList(DetachedCriteria dc, int firstResult, int maxResult) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		return criteria.list();
	}

	/**
	 * 离线查询
	 */
	public List findByDetached(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getSession()).list();
	}
}
