/**
 * 
 */
package com.xbrother.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.xbrother.common.entity.UUIDEntity;

/**
 * @author qhcui
 * 
 */
public interface IBaseDao {

	/** Methods from {@link HibernateOperations} */
	<T extends UUIDEntity> T get(Class<T> entityClass, Serializable id) throws DataAccessException;

	<T extends UUIDEntity> T load(Class<T> entityClass, Serializable id) throws DataAccessException;

	<T extends UUIDEntity> List<T> loadAll(Class<T> entityClass) throws DataAccessException;

	<T extends UUIDEntity> T saveOrUpdate(T entity) throws DataAccessException;

	// <T extends UUIDEntity> T update(T entity) throws DataAccessException;

	// void merge(Object entity) throws DataAccessException;

	void delete(Object entity) throws DataAccessException;

	Integer delete(String hql) throws DataAccessException;

	@SuppressWarnings("rawtypes")
	void deleteAll(Collection entities) throws DataAccessException;

	/** End of methods from {@link HibernateOperations} */
	public <T extends UUIDEntity> List<T> getAll(Class<T> c);

	/**
	 * Is this a new transient instance? Returns true if the object is new
	 */
	// public boolean isTransient(Object entity);

	// public <T> List<T> findByCriteria(DetachedCriteria detachedCriteria,int
	// firstResult,int maxResults);

	// public <T> List<T> findByCriteria(DetachedCriteria detachedCriteria);

	/**
	 * Finds a single object using the query.
	 * 
	 * @param queryString
	 * @return
	 * @throws NonUniqueResultException
	 *             if the number of results are more than one
	 */
	public <T extends UUIDEntity> T findUnique(String queryString) throws NonUniqueResultException;

	/**
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 * @throws NonUniqueResultException
	 */
	public <T extends UUIDEntity> T findUnique(String queryString, Object... values) throws NonUniqueResultException;

	/**
	 * 
	 * @param queryString
	 * @param parameterNames
	 * @param parameters
	 * @return
	 * @throws NonUniqueResultException
	 */
	// public <T> T findUniqueByNamedParameter(String queryString, String[]
	// parameterNames, Object[] parameters) throws NonUniqueResultException;

	/**
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public <T extends UUIDEntity> List<T> find(String queryString, Object... values) throws DataAccessException;

	/**
	 * 
	 * @param queryString
	 * @param parameterNames
	 * @param parameters
	 * @return
	 * @throws DataAccessException
	 */
	// public <T> List<T> findByNamedParameters(String queryString, String[]
	// parameterNames, Object[] parameters) throws DataAccessException;

	/**
	 * 
	 * @param queryString
	 * @return
	 */
	public <T extends UUIDEntity> List<T> find(String queryString);

	/**
	 * Triggers paginated query
	 * 
	 * @param queryString
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	public <T extends UUIDEntity> List<T> find(String queryString, int firstResult, int maxResults)
			throws DataAccessException;

	/**
	 * get count rows
	 * 
	 * @date 2013-7-30
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 */
	public Long findCount(String queryString) throws DataAccessException;

	/**
	 * 
	 * @param queryName
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	// public <T> T findUniqueByNamedQuery(String queryName, Object... values)
	// throws DataAccessException;

	/**
	 * 
	 * @param queryName
	 * @param paramNames
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	// public <T> T findUniqueByNamedQuery(String queryName, String[]
	// paramNames, Object[] params) throws DataAccessException;

	/**
	 * 
	 * @param queryName
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	// public <T> List<T> findByNamedQuery(String queryName, Object... values)
	// throws DataAccessException;

	/**
	 * 
	 * @param queryName
	 * @param paramNames
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	// public <T> List<T> findByNamedQuery(String queryName, String[]
	// paramNames, Object[] params) throws DataAccessException;

	/**
	 * Flushes to synchronize the current session data
	 */
	public void flush();

	/**
	 * Evicts an entity from the current hibernate session
	 * 
	 * @param entity
	 */
	public void evict(Object entity);

	/**
	 * Finds the entity name of the object passed. This method takes care of
	 * {@link HibernateProxy}
	 * 
	 * @param entity
	 * @return
	 */
	public String getEntityName(Object entity);

	/**
	 * Gets the identifier if available of the persisted object
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable getIdentifier(Object entity);

	/**
	 * Initializes the entity if it is a proxy, does nothing if the passed
	 * object is not a proxy
	 * 
	 * @param entity
	 * @return
	 */
	// public Object hydrate(Object entity);

	/**
	 * Gets the current session if any
	 * 
	 * @return
	 */
	public Session getSession();
}
