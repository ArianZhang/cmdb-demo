/**
 * 
 */
package com.xbrother.common.service;

import java.util.List;

import com.xbrother.common.entity.BaseEntity;
import com.xbrother.common.entity.UUIDEntity;
import com.xbrother.common.query.Condition;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-11-14
 * @version 1.0
 */
public interface IBaseService {
	
	/**
	 * 根据主键获取对应的Entity对象
	 * @date 2013-11-17
	 * @param uid
	 * @param entityClass
	 * @return
	 */
	<T extends UUIDEntity> T find(String uid,Class<T> entityClass);
	
	/**
	 * 用于增量下载
	 * @param lastUpdate
	 * @param entityClass
	 * @return
	 */
	<T extends BaseEntity> List<T> findByLastUpdate(Long lastUpdate,Class<T> entityClass);
	
	/**
	 * 用于实体新增
	 * @param entity
	 * @return
	 */
	<T extends UUIDEntity> T addNew(T entity);
	
	/**
	 * 用于实体更新
	 * @param entity
	 * @return
	 */
	<T extends UUIDEntity> T update(T entity);
	
	/**
	 * 用于实体新增或更新
	 * @param entity
	 * @return
	 */
	<T extends UUIDEntity> T saveOrUpdate(T entity);
	
	/**
	 * 用于实体逻辑删除
	 * @param entity
	 * @return
	 */
	<T extends BaseEntity> T logicDelete(T entity);
	
	/**
	 * 用于按条件查询
	 * @param conditions
	 * @param entityClass
	 * @return
	 */
	<T extends UUIDEntity> List<T> query(List<Condition> conditions,Class<T> entityClass);
	
}
