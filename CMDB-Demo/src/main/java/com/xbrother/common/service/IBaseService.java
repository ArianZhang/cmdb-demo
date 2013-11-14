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
	<T extends BaseEntity> T saveOrUpdate(T entity);
	List<? extends BaseEntity> query();
}
