/**
 * 
 */
package com.xbrother.common.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbrother.common.dao.IBaseDao;
import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.dto.ui.PaginationRequestDTO;
import com.xbrother.common.dto.ui.PaginationResponseDTO;
import com.xbrother.common.dto.utils.ConvertUtils;
import com.xbrother.common.entity.BaseEntity;
import com.xbrother.common.entity.IdEntity;
import com.xbrother.common.entity.enums.Status;
import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.BasicBizsExceptionCode;
import com.xbrother.common.query.Condition;
import com.xbrother.common.query.utils.QueryUtils;
import com.xbrother.common.service.IBaseService;

/**
 * common service
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-28
 * @version 1.0
 */
@Service
@Transactional
public class BaseService implements IBaseService {

	private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

	@Autowired
	protected IBaseDao baseDao;

	@Override
	public <T extends SuperDTO, E extends IdEntity> T getDTOById(Integer id, Class<T> dtoClass, Class<E> entityClass) {
		E entity = baseDao.load(entityClass, id);
		return ConvertUtils.convert2DTO(entity, dtoClass, entityClass);
	}

	@Override
	public <T extends SuperDTO, E extends IdEntity> T saveOrUpdateDTO(SuperDTO dto, Class<T> dtoClass,
			Class<E> entityClass) {
		E entity = ConvertUtils.reverse2Entity(dto, dtoClass, entityClass, baseDao);
		return ConvertUtils.convert2DTO(entity, dtoClass, entityClass);
	}

	// @Override
	// public <T extends SuperDTO, E extends IdEntity> T updateDTO(SuperDTO dto,
	// Class<T> dtoClass, Class<E> entityClass) {
	// E entity = ConvertUtils.reverse2Entity(dto, dtoClass, entityClass,
	// baseDao);
	// return ConvertUtils.convert2DTO(entity, dtoClass, entityClass);
	// }

	@Override
	public <T extends SuperDTO, E extends IdEntity> void deleteDTOById(Integer id, Class<E> entityClass) {
		try {
			E entity = entityClass.newInstance();
			entity.setId(id);
			if (entityClass.getSuperclass().equals(BaseEntity.class)) {
				BaseEntity.class.cast(entity).setStatus(Status.invalid.value);
				baseDao.saveOrUpdate(entity);
			} else {
				baseDao.delete(entity);
			}
		} catch (Exception e) {
			throw new BizsException(e, BasicBizsExceptionCode.DELETE_ENTITY_ERROR);
		}
	}
	
	@Override
	public <T extends SuperDTO, E extends IdEntity> void recoverDTOById(Integer id, Class<E> entityClass) {
		try {
			E entity = entityClass.newInstance();
			entity.setId(id);
			if (entityClass.getSuperclass().equals(BaseEntity.class)) {
				BaseEntity.class.cast(entity).setStatus(Status.valid.value);
				baseDao.saveOrUpdate(entity);
			}
		} catch (Exception e) {
			throw new BizsException(e, BasicBizsExceptionCode.RECOVER_ENTITY_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SuperDTO, E extends IdEntity> List<T> findAllDTOsByLastUpdate(Date date, Class<T> dtoClass,
			Class<E> entityClass) {
		List<E> entitys = null;
		if (date == null) {
			entitys = baseDao.find("from " + entityClass.getName());
		} else {
			entitys = baseDao.find("from " + entityClass.getName() + " entity where entity.updatetime >= ?", date);
		}
		return ConvertUtils.converts2DTO4Mobile(entitys, dtoClass, entitys.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SuperDTO, E extends IdEntity> PaginationResponseDTO<T> findDTOsByPagination(
			PaginationRequestDTO paginationRequestDTO, Class<T> dtoClass, Class<E> entityClass) {
		int firstResult = (paginationRequestDTO.getPage() - 1) * paginationRequestDTO.getRows();
		int maxResult = paginationRequestDTO.getRows();
//		if (BaseEntity.class.equals(entityClass.getSuperclass())) {
//			paginationRequestDTO.getConditions().add(Condition.VALID_STATUS);
//		}
		String sql = QueryUtils.dynamicWhereSql(paginationRequestDTO.getConditions(), entityClass);
		String orderBySql = QueryUtils.dynamicOrderBySqlParts(paginationRequestDTO.getOrderBies());
		List<E> entitys = baseDao.find(sql + orderBySql, firstResult, maxResult);
		PaginationResponseDTO<T> dto = new PaginationResponseDTO<T>();
		dto.setRows(ConvertUtils.converts2DTO(entitys, dtoClass, entitys.getClass()));
		dto.setTotal(baseDao.findCount("select count(id) " + sql));
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SuperDTO, E extends IdEntity> List<T> findAllDTOs(Class<T> dtoClass, Class<E> entityClass) {
		StringBuilder sql = new StringBuilder("from ");
		sql.append(entityClass.getName()).append(" entity where entity.status = '").append(Status.valid.value)
				.append("'");
		List<E> entitys = baseDao.find(sql.toString());
		return ConvertUtils.converts2DTO(entitys, dtoClass, entitys.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends SuperDTO, E extends IdEntity> List<T> findDTOsByConditions(List<Condition> conditions,
			Class<T> dtoClass, Class<E> entityClass) {
		String sql = QueryUtils.dynamicWhereSql(conditions, entityClass);
		List<E> entitys = baseDao.find(sql.toString());
		return ConvertUtils.converts2DTO(entitys, dtoClass, entitys.getClass());
	}

}
