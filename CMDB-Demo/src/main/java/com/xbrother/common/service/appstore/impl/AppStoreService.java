/**
 * 
 */
package com.xbrother.common.service.appstore.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbrother.common.dao.IBaseDao;
import com.xbrother.common.dto.ui.PaginationRequestDTO;
import com.xbrother.common.dto.ui.PaginationResponseDTO;
import com.xbrother.common.dto.utils.ConvertUtils;
import com.xbrother.common.entity.appstore.AppSpace;
import com.xbrother.common.entity.appstore.AppVersion;
import com.xbrother.common.entity.enums.Status;
import com.xbrother.common.query.utils.QueryUtils;
import com.xbrother.common.service.appstore.IAppStoreService;
import com.xbrother.common.utils.GUIDUtils;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-9-23
 * @version 1.0
 */
@Service
@Transactional
public class AppStoreService implements IAppStoreService {

	@Autowired
	IBaseDao baseDao;

	@Override
	public PaginationResponseDTO<AppSpace> appSpaceDataGridList(PaginationRequestDTO dto) {
		int firstResult = (dto.getPage() - 1) * dto.getRows();
		int maxResult = dto.getRows();
		String sql = QueryUtils.dynamicWhereSql(dto.getConditions(), AppSpace.class);
		String orderBySql = QueryUtils.dynamicOrderBySqlParts(dto.getOrderBies());
		List<AppSpace> entitys = baseDao.find(sql + orderBySql, firstResult, maxResult);
		PaginationResponseDTO<AppSpace> result = new PaginationResponseDTO<AppSpace>();
		result.setRows(entitys);
		result.setTotal(baseDao.findCount("select count(id) " + sql));
		return result;
	}

	@Override
	public PaginationResponseDTO<AppVersion> versionDataGridList(PaginationRequestDTO dto) {
		int firstResult = (dto.getPage() - 1) * dto.getRows();
		int maxResult = dto.getRows();
		String sql = QueryUtils.dynamicWhereSql(dto.getConditions(), AppVersion.class);
		String orderBySql = QueryUtils.dynamicOrderBySqlParts(dto.getOrderBies());
		List<AppVersion> entitys = baseDao.find(sql + orderBySql, firstResult, maxResult);
		PaginationResponseDTO<AppVersion> result = new PaginationResponseDTO<AppVersion>();
		result.setRows(entitys);
		result.setTotal(baseDao.findCount("select count(id) " + sql));
		return result;
	}

	@Override
	public AppSpace getAppSpace(Integer id) {
		return baseDao.get(AppSpace.class, id);
	}

	@Override
	public List<AppSpace> getAllAppSpace() {
		return baseDao.find("from " + AppSpace.class.getName() + " asp where asp.status = '" + Status.valid.value
				+ "' order by asp.id desc");
	}

	@Override
	public AppSpace saveOrUpdateAppSpace(AppSpace appSpace) {
		if (appSpace.getId() == null) {
			appSpace.setCode(GUIDUtils.generate());
		}
		return baseDao.saveOrUpdate(appSpace);
	}

	@Override
	public int deleteAppSpace(Integer id) {
		AppSpace appSpace = new AppSpace();
		appSpace.setId(id);
		appSpace.setStatus(Status.invalid.value);
		baseDao.saveOrUpdate(appSpace);
		return 1;
	}

	@Override
	public int recoverAppSpace(Integer id) {
		AppSpace appSpace = new AppSpace();
		appSpace.setId(id);
		appSpace.setStatus(Status.valid.value);
		baseDao.saveOrUpdate(appSpace);
		return 1;
	}

	@Override
	public AppVersion getVersion(Integer id) {
		return baseDao.get(AppVersion.class, id);
	}

	@Override
	public AppVersion saveOrUpdateVersion(AppVersion version) {
		return baseDao.saveOrUpdate(version);
	}

	@Override
	public int recoverAppVersion(Integer id) {
		AppVersion version = new AppVersion();
		version.setId(id);
		version.setStatus(Status.valid.value);
		baseDao.saveOrUpdate(version);
		return 1;
	}

	@Override
	public int deleteAppVersion(Integer id) {
		AppVersion version = new AppVersion();
		version.setId(id);
		version.setStatus(Status.invalid.value);
		baseDao.saveOrUpdate(version);
		return 1;
	}

	@Override
	public AppVersion getLastReleasedApp(String spaceCode) {
		List<AppVersion> versions = baseDao.find("from " + AppVersion.class.getName()
				+ " av where av.appSpace.code = '" + spaceCode + "' and av.status = '"+Status.valid.value+"' order by createtime desc", 0, 1);
		for (AppVersion version : versions) {
			return version;
		}
		return null;
	}

}
