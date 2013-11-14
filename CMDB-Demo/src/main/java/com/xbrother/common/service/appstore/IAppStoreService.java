/**
 * 
 */
package com.xbrother.common.service.appstore;

import java.util.List;

import com.xbrother.common.dto.ui.PaginationRequestDTO;
import com.xbrother.common.dto.ui.PaginationResponseDTO;
import com.xbrother.common.entity.appstore.AppSpace;
import com.xbrother.common.entity.appstore.AppVersion;

/**
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-9-23
 * @version 1.0
 */
public interface IAppStoreService {

	PaginationResponseDTO<AppSpace> appSpaceDataGridList(PaginationRequestDTO dto);
	PaginationResponseDTO<AppVersion> versionDataGridList(PaginationRequestDTO dto);
	AppSpace getAppSpace(Integer id);
	List<AppSpace> getAllAppSpace();
	AppSpace saveOrUpdateAppSpace(AppSpace appSpace);
	int deleteAppSpace(Integer id);
	int recoverAppSpace(Integer id);
	AppVersion getVersion(Integer id);
	AppVersion saveOrUpdateVersion(AppVersion version);
	int recoverAppVersion(Integer id);
	int deleteAppVersion(Integer id);
	AppVersion getLastReleasedApp(String spaceCode);
	

}
