package com.xbrother.common.service;

import java.util.Date;
import java.util.List;

import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.dto.ui.PaginationRequestDTO;
import com.xbrother.common.dto.ui.PaginationResponseDTO;
import com.xbrother.common.entity.IdEntity;
import com.xbrother.common.query.Condition;

/**
 * common service
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-28
 * @version 1.0
 */
public interface IBaseService {

	<T extends SuperDTO, E extends IdEntity> List<T> findAllDTOs(Class<T> dtoClass, Class<E> entityClass);

	<T extends SuperDTO, E extends IdEntity> T getDTOById(Integer id, Class<T> dtoClass, Class<E> entityClass);

	<T extends SuperDTO, E extends IdEntity> T saveOrUpdateDTO(SuperDTO dto, Class<T> dtoClass, Class<E> entityClass);

	// <T extends SuperDTO, E extends IdEntity> T updateDTO(SuperDTO dto,
	// Class<T> dtoClass, Class<E> entityClass);

	<T extends SuperDTO, E extends IdEntity> void deleteDTOById(Integer id, Class<E> entityClass);
	
	<T extends SuperDTO, E extends IdEntity> void recoverDTOById(Integer id, Class<E> entityClass);
	

	<T extends SuperDTO, E extends IdEntity> List<T> findAllDTOsByLastUpdate(Date date, Class<T> dtoClass,
			Class<E> entityClass);

	<T extends SuperDTO, E extends IdEntity> PaginationResponseDTO<T> findDTOsByPagination(
			PaginationRequestDTO paginationRequestDTO, Class<T> dtoClass, Class<E> entityClass);

	<T extends SuperDTO, E extends IdEntity> List<T> findDTOsByConditions(List<Condition> conditions,
			Class<T> dtoClass, Class<E> entityClass);

}
