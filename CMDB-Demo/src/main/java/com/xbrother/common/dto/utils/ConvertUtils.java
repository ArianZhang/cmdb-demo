package com.xbrother.common.dto.utils;

import java.lang.reflect.Method;
import java.util.List;

import com.xbrother.common.constants.Entity2DTOConvertReverseMethodName;
import com.xbrother.common.dao.IBaseDao;
import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.entity.IdEntity;
import com.xbrother.common.exception.BizsException;
import com.xbrother.common.exception.enums.UtilsExceptionCode;
import com.xbrother.common.exception.utils.ExceptionUtils;

/**
 * 
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-28
 * @version 1.0
 */
public class ConvertUtils {

	/**
	 * convert entity to dto object.
	 * 
	 * @date 2013-7-28
	 * @param entity
	 * @param dtoClass
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SuperDTO, E extends IdEntity> T convert2DTO(IdEntity entity, Class<T> dtoClass,
			Class<E> entityClass) {
		Method method = null;
		T dto = null;
		try {
			method = dtoClass.getMethod(Entity2DTOConvertReverseMethodName.CONVERT, entityClass);
			dto = (T) method.invoke(null, entity);
		} catch (Exception e1) {
			handException(e1);
		}
		return dto;
	}

	/**
	 * convert entity to dto object. just for mobile APP.
	 * 
	 * @date 2013-7-28
	 * @param entity
	 * @param dtoClass
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SuperDTO, E extends IdEntity> T convert2DTO4Mobile(IdEntity entity, Class<T> dtoClass,
			Class<E> entityClass) {
		Method method = null;
		T dto = null;
		try {
			method = dtoClass.getMethod(Entity2DTOConvertReverseMethodName.CONVERT_4_MOBILE, entityClass);
			dto = (T) method.invoke(null, entity);
		} catch (Exception e1) {
			handException(e1);
		}
		return dto;
	}

	/**
	 * reverse dto to entity object.
	 * 
	 * @date 2013-7-28
	 * @param dto
	 * @param dtoClass
	 * @param entityClass
	 * @param baseDao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SuperDTO, E extends IdEntity> E reverse2Entity(SuperDTO dto, Class<T> dtoClass,
			Class<E> entityClass, IBaseDao baseDao) {
		Method method = null;
		E entity = null;
		try {
			method = dtoClass.getMethod(Entity2DTOConvertReverseMethodName.REVERSE, dtoClass, IBaseDao.class);
			entity = (E) method.invoke(null, dto, baseDao);
		} catch (Exception e1) {
			handException(e1);
		}
		return entity;
	}

	/**
	 * reverse dto to entity object.just for mobile APP.
	 * 
	 * @date 2013-7-28
	 * @param dto
	 * @param dtoClass
	 * @param entityClass
	 * @param baseDao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SuperDTO, E extends IdEntity> E reverse2Entity4Mobile(SuperDTO dto, Class<T> dtoClass,
			Class<E> entityClass, IBaseDao baseDao) {
		Method method = null;
		E entity = null;
		try {
			method = dtoClass.getMethod(Entity2DTOConvertReverseMethodName.REVERSE_4_MOBILE, dtoClass, IBaseDao.class);
			entity = (E) method.invoke(null, dto, baseDao);
		} catch (Exception e1) {
			handException(e1);
		}
		return entity;
	}

	/**
	 * converts entity list to dto list.
	 * 
	 * @date 2013-7-28
	 * @param entitys
	 * @param dtoClass
	 * @param entitysClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SuperDTO, E extends IdEntity, ES extends List<E>> List<T> converts2DTO(List<E> entitys,
			Class<T> dtoClass, Class<ES> entitysClass) {
		Method method = null;
		List<T> dtos = null;
		try {
			method = dtoClass.getMethod(Entity2DTOConvertReverseMethodName.CONVERTS, entitysClass);
			dtos = (List<T>) method.invoke(null, entitys);
		} catch (Exception e1) {
			handException(e1);
		}
		return dtos;
	}

	/**
	 * converts entity list to dto list. just for Mobile APP.
	 * 
	 * @date 2013-7-28
	 * @param entitys
	 * @param dtoClass
	 * @param entitysClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SuperDTO, E extends IdEntity, ES extends List<E>> List<T> converts2DTO4Mobile(
			List<E> entitys, Class<T> dtoClass, Class<ES> entitysClass) {
		Method method = null;
		List<T> dtos = null;
		try {
			method = dtoClass.getMethod(Entity2DTOConvertReverseMethodName.CONVERTS_4_MOBILE, entitysClass);
			dtos = (List<T>) method.invoke(null, entitys);
		} catch (Exception e1) {
			handException(e1);
		}
		return dtos;
	}

	private static void handException(Exception e) {
		BizsException bizse = ExceptionUtils.findBizsException(e);
		if (bizse != null) {
			throw bizse;
		}
		if (e instanceof BizsException) {
			throw (BizsException) e;
		} else {
			throw new BizsException(e, UtilsExceptionCode.CONVERT_ERROR);
		}
	}
}
