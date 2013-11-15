package com.xbrother.cmdb.rs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.xbrother.cmdb.entity.ConfigurationItem;
import com.xbrother.cmdb.entity.ConfigurationItemAttr;
import com.xbrother.cmdb.entity.ConfigurationItemSpot;
import com.xbrother.cmdb.entity.ConfigurationItemSpotSegment;
import com.xbrother.common.constants.HeaderKey;
import com.xbrother.common.rs.AbstractRsTest;
import com.xbrother.common.utils.JsonUtils;

public class ConfigurationItemRsTest extends AbstractRsTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationItemRsTest.class);

	@Override
	protected WebResource pathResource() {
		return resource.path("/basicdatamgt/ci");
	}

	@Test
	public void testSaveOrUpdate() {
		ConfigurationItem ci = new ConfigurationItem();
		ci.setTemplateUid("1");
		ci.setComputerRoomUid("7b4a187524874288806fe60fdb8ddb7a");
		ci.setLocationUid("946c8f390bcc4778bd578cd08d7112aa11");
		ci.setTypeId("010101");
		ci.setName("艾默生 UPS 200");
		ci.setManufacturerUid("1");
		ci.setServiceProviderUid("1");
		ci.setModelCode("UH3R_0200");
		ci.setPurpose("持续供电，稳压作用");
		ci.setSeriesNo("UH3R020001110111");

		ConfigurationItemAttr attr = new ConfigurationItemAttr();
		Set<ConfigurationItemAttr> attrs = new HashSet<ConfigurationItemAttr>();
		attrs.add(attr);
		attr.setAttrType(1);
		attr.setSortNo(1);
		attr.setName("powerRate");
		attr.setDisplayName("额定功率");
		attr.setAttrValue("2KVA");
		attr.setValueType(1);
		attr.setRequried(0);
		ci.setAttributes(attrs);

		ConfigurationItemSpot spot = new ConfigurationItemSpot();
		Set<ConfigurationItemSpot> spots = new HashSet<ConfigurationItemSpot>();
		spots.add(spot);
		spot.setId(1);
		spot.setName("交流输入相电压A");
		spot.setNormalValue("");
		spot.setExceptionValue("");
		spot.setNormalMinValue("215");
		spot.setNormalMaxValue("255");
		spot.setValueType(2);

		ConfigurationItemSpotSegment segment = new ConfigurationItemSpotSegment();
		Set<ConfigurationItemSpotSegment> segments = new HashSet<ConfigurationItemSpotSegment>();
		segments.add(segment);
		segment.setPriorNo(1);
		segment.setArithmeticSign(">=");
		segment.setThresholdValue("280");
		segment.setIncidentDesc("UPS指标{0}，达到{4}V，超过{1}，事件等级：{3}，请速处理！");
		spot.setThresholdSegments(segments);

		ci.setMonitorSpots(spots);

		ClientResponse response = pathResource().path("saveOrUpdate").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, JsonUtils.toJson(ci));
		
		Assert.assertEquals(200, response.getStatus());
	
	}

	@Test
	public void testSyn() {
		ClientResponse response = pathResource().path("syn").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		LOGGER.info("response is " + response.getEntity(String.class));
		Assert.assertEquals(200, response.getStatus());

		response = resource.path("syn").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
				.header(HeaderKey.LASTUPDATE, System.currentTimeMillis()).get(ClientResponse.class);
		LOGGER.info("response is " + response.getEntity(String.class));
		Assert.assertEquals(304, response.getStatus());
	}

}
