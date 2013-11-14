package com.xbrother.common.rs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.xbrother.common.aop.annotation.Validate;
import com.xbrother.common.context.CurrentContext;
import com.xbrother.common.dto.SuperDTO;
import com.xbrother.common.dto.ui.PaginationRequestDTO;
import com.xbrother.common.entity.IdEntity;
import com.xbrother.common.query.Condition;
import com.xbrother.common.report.ExcelReport;
import com.xbrother.common.rs.constants.PathConst;
import com.xbrother.common.service.IBaseService;
import com.xbrother.common.utils.StreamUtils;

/**
 * It's a abstract restful web resource class.<br/>
 * this class defined many very useful common method for sub restful web
 * resource class.
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-7-21
 * @version 1.0
 */
public abstract class SuperRs<T extends SuperDTO, E extends IdEntity> extends AbstractRs {

	@Autowired
	protected IBaseService baseService;
	protected Class<T> dtoClass;
	protected Class<E> entityClass;
	protected ExcelReport report;

	@SuppressWarnings("unchecked")
	public SuperRs() {
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = null;
		if (type != null && type instanceof ParameterizedType) {
			parameterizedType = (ParameterizedType) type;
		}
		if (parameterizedType != null) {
			this.dtoClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
			this.entityClass = (Class<E>) parameterizedType.getActualTypeArguments()[1];
		}
	}

	/**
	 * synchronize data
	 * 
	 * @param lastUpdate
	 * @return
	 */
	@Path("/syn")
	@GET
	public Response getData(@HeaderParam("lastUpdate") String lastUpdate) {
		List<T> dtos;
		if (lastUpdate != null && !"".equals(lastUpdate)) {
			Date lastUpdateTime = new Date(Long.valueOf(lastUpdate));
			dtos = baseService.findAllDTOsByLastUpdate(lastUpdateTime, dtoClass, entityClass);
		} else {
			dtos = baseService.findAllDTOsByLastUpdate(null, dtoClass, entityClass);
		}
		return createSynResult(dtos);
	}

	/**
	 * server for datagrid
	 * 
	 * @date 2013-7-28
	 * @param dto
	 * @return
	 */
	@Path("/datagridlist")
	@POST
	public Response dataGridList(PaginationRequestDTO dto) {
		return ok(baseService.findDTOsByPagination(dto, dtoClass, entityClass));
	}

	/**
	 * server for data grid export
	 * 
	 * @date 2013-8-5
	 * @param conditions
	 * @return
	 */
	@Path("/datagridlist/export")
	@POST
	@Produces(MediaType.TEXT_HTML)
	public Response dataGridListExport(List<Condition> conditions) {
		//OrderReport report = new OrderReport();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		report.writeStream(report.createHSSFWorkbook(dtoClass,
				baseService.findDTOsByConditions(conditions, dtoClass, entityClass)), os);

		String realRootPath = CurrentContext.getHttpServletRequest().getServletContext().getRealPath("/");
		String path = PathConst.FILE_UPLOAD_PLACE + System.nanoTime() + report.getFileName();
		File file = new File(realRootPath, path);
		StreamUtils.writeFile(file, new ByteArrayInputStream(os.toByteArray()));
		return ok(path);
	}

	/**
	 * save dto
	 * 
	 * @date 2013-7-28
	 * @param dto
	 * @return
	 */
	@Path("/save")
	@POST
	@Validate
	public Response save(T dto) {
		T t = baseService.saveOrUpdateDTO(dto, dtoClass, entityClass);
		return ok(t);
	}

	/**
	 * update dto
	 * 
	 * @date 2013-7-28
	 * @param dto
	 * @return
	 */
	@Path("/update")
	@PUT
	@Validate
	public Response update(T dto) {
		T t = baseService.saveOrUpdateDTO(dto, dtoClass, entityClass);
		return ok(t);
	}

	/**
	 * delete dtos
	 * 
	 * @date 2013-7-28
	 * @param dtos
	 * @return
	 */
	@Path("/delete")
	@PUT
	public Response delete(List<Integer> ids) {
		for (Integer id : ids) {
			baseService.deleteDTOById(id, entityClass);
		}
		return ok();
	}

	/**
	 * recover dtos
	 * 
	 * @date 2013-8-7
	 * @param ids
	 * @return
	 */
	@Path("/recover")
	@PUT
	public Response recover(List<Integer> ids) {
		for (Integer id : ids) {
			baseService.recoverDTOById(id, entityClass);
		}
		return ok();
	}

	/**
	 * get dto
	 * 
	 * @date 2013-7-28
	 * @param id
	 * @return
	 */
	@Path("/get")
	@GET
	public Response get(@QueryParam("id") Integer id) {
		T t = baseService.getDTOById(id, dtoClass, entityClass);
		return ok(t);
	}

	@Path("/getAll")
	@GET
	public Response getAll() {
		return ok(baseService.findAllDTOs(dtoClass, entityClass));
	}
	
	public Class<T> getDTOClass(){
		return this.dtoClass;
	}
}
