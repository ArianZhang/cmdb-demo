package com.xbrother.cmdb.rs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.xbrother.cmdb.entity.ConfigurationItem;
import com.xbrother.cmdb.entity.ConfigurationItemAttr;
import com.xbrother.cmdb.entity.ConfigurationItemSpot;
import com.xbrother.cmdb.entity.ConfigurationItemSpotSegment;
import com.xbrother.cmdb.entity.utils.EntityRelationUtils;
import com.xbrother.common.rs.AbstractRs;
import com.xbrother.common.service.impl.BaseService;
import com.xbrother.common.utils.CollectionUtils;
import com.xbrother.common.utils.JsonUtils;
import com.xbrother.common.utils.PropertiesUtils;
import com.xbrother.common.utils.RabbitMQUtils;

/**
 * 用于构建CI的对外Restful接口
 * 
 * @author Arian Zhang
 * @email arian_zhang@foxmail.com
 * @date 2013-11-14
 * @version 1.0
 */
@Component
@Path("/basicdatamgt/ci")
public class ConfigurationItemRs extends AbstractRs {

	private String mqHostName;
	private String routerKey;
	private String exchangeName;
	{
		Properties prop = PropertiesUtils.getProperties("/mq-config.properties");
		routerKey = prop.getProperty("ci_router_key", "ci");
		exchangeName = prop.getProperty("ci_exchange_name", "ci_topics");
		mqHostName = prop.getProperty("mq_host_name", "localhost");
	}

	@Autowired
	BaseService baseService;

	

	/**
	 * 用于处理WEBUI发起CI保存操作接口
	 * 
	 * @param ci
	 */
	@POST
	@Path("saveOrUpdate")
	public Response saveOrUpdate(ConfigurationItem ci) {
		int actionType = StringUtils.isEmpty(ci.getUid()) ? 1 : 2;
		EntityRelationUtils.initialCIRelation(ci);
		ci = baseService.saveOrUpdate(ci);
		// build message header and send the news.
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("actionType", actionType);
		headers.put("uid", ci.getUid());
		headers.put("lastUpdate", ci.getUpdateTime().getTime());
		sendTopic(headers, JsonUtils.toJson(ci));
		return ok();
	}

	/**
	 * 用户发送CI更新消息
	 * 
	 * @param headers
	 * @param jsonMessage
	 */
	private void sendTopic(Map<String, Object> headers, String jsonMessage) {
		BasicProperties properties = new BasicProperties.Builder().contentType(MediaType.APPLICATION_JSON).contentEncoding("UTF-8").headers(headers).build();
		// send topic message.
		RabbitMQUtils.sendTopic(mqHostName, exchangeName, routerKey, properties, jsonMessage);
	}
}
