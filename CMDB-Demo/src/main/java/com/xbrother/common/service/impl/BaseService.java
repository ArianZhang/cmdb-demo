package com.xbrother.common.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbrother.common.constants.DbOperationConst;
import com.xbrother.common.dao.IBaseDao;
import com.xbrother.common.entity.BaseEntity;
import com.xbrother.common.entity.enums.RowStatus;
import com.xbrother.common.service.IBaseService;

@Service
@Transactional
public class BaseService implements IBaseService {

	@Autowired
	IBaseDao baseDao;

	@Override
	public <T extends BaseEntity> T saveOrUpdate(T entity) {
		return baseDao.saveOrUpdate(entity);
	}

	@Override
	public <T extends BaseEntity> List<T> findByLastUpdate(Long lastUpdate, Class<T> entityClass) {
		if (lastUpdate == null || lastUpdate.equals(0)) {
			return baseDao.find("from " + entityClass.getName() + " entity order by entity.updateTime ", 0, DbOperationConst.MAX_ROW);
		}
		return baseDao.find("from " + entityClass.getName() + " entity where entity.updateTime >= ? order by entity.updateTime ", new Date(lastUpdate), 0,
				DbOperationConst.MAX_ROW);
	}

	@Override
	public <T extends BaseEntity> T addNew(T entity) {
		return baseDao.addNew(entity);
	}


	@Override
	public <T extends BaseEntity> T update(T entity) {
		return baseDao.update(entity);
	}

	@Override
	public <T extends BaseEntity> T logicDelete(T entity) {
		entity.setRowStatus(RowStatus.invalid.value);
		return baseDao.update(entity);
	}

}
