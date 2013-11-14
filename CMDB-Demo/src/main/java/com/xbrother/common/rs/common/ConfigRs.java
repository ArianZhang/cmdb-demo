package com.xbrother.common.rs.common;

import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.xbrother.common.constants.PropertiesFilePath;
import com.xbrother.common.rs.AbstractRs;
import com.xbrother.common.rs.constants.PathConst;
import com.xbrother.common.utils.PropertiesUtils;

@Path(ConfigRs.PATH)
@Component
public class ConfigRs extends AbstractRs {
	public final static String PATH = PathConst.COMMON + "/config";

	@GET
	@Path("/{configKey}")
	public Response getConfigByName(@PathParam("configKey") String configKey) {
		Properties prop = PropertiesUtils.getProperties(PropertiesFilePath.CONFIG_PROPERTIES);
		return ok(prop.getProperty(configKey));
	}

	@GET
	public Response getAllConfig() {
		Properties prop = PropertiesUtils.getProperties(PropertiesFilePath.CONFIG_PROPERTIES);
		return ok(prop);
	}
}
