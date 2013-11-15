/**
 * 
 */
package com.xbrother.common.service;

import java.util.List;

import com.xbrother.common.entity.BaseEntity;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-11-14
 * @version 1.0
 */
public interface IBaseService {
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
	<T extends BaseEntity> T addNew(T entity);
	
	/**
	 * 用于实体更新
	 * @param entity
	 * @return
	 */
	<T extends BaseEntity> T update(T entity);
	
	/**
	 * 用于实体新增或更新
	 * @param entity
	 * @return
	 */
	<T extends BaseEntity> T saveOrUpdate(T entity);
	
	/**
	 * 用于实体逻辑删除
	 * @param entity
	 * @return
	 */
	<T extends BaseEntity> T logicDelete(T entity);
	
}
