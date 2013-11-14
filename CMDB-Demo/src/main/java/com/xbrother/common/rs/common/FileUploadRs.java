package com.xbrother.common.rs.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.xbrother.common.constants.SessionKey;
import com.xbrother.common.context.ContextScope;
import com.xbrother.common.context.CurrentContext;
import com.xbrother.common.rs.AbstractRs;
import com.xbrother.common.rs.constants.PathConst;
import com.xbrother.common.utils.StreamUtils;

/**
 * Description:
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @Date 2013-7-22 上午11:09:41
 * @since v1.0.0
 */
@Path(FileUploadRs.PATH)
@Component
public class FileUploadRs extends AbstractRs {

	public final static String PATH = PathConst.COMMON + "/fileUpload";

	private final static Logger LOGGER = LoggerFactory.getLogger(FileUploadRs.class);
	
	@Path("/upload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public Response uploadFile(@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
		String realRootPath = CurrentContext.getHttpServletRequest().getServletContext().getRealPath("/");
		String path = PathConst.FILE_UPLOAD_PLACE + System.nanoTime()
				+ fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf("."));
		File file = new File(realRootPath, path);
		StreamUtils.writeFile(file, inputStream);
		CurrentContext.put(SessionKey.UPLOADED_FILE_PREFIX_KEY + fileDetail.getName(), path, ContextScope.SESSION);
		LOGGER.debug("file uploaded stored in path :" + path);
		return ok(path);
	}
}
