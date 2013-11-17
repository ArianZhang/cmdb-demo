package com.xbrother.cmdb.mq;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.xbrother.cmdb.entity.ConfigurationItem;
import com.xbrother.cmdb.entity.utils.EntityRelationUtils;
import com.xbrother.common.entity.enums.RowStatus;
import com.xbrother.common.service.IBaseService;
import com.xbrother.common.utils.JsonUtils;
import com.xbrother.common.utils.PropertiesUtils;

@Component
public class SubscribeCIsTopic implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(SubscribeCIsTopic.class);
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
	IBaseService baseService;

	public SubscribeCIsTopic() {

	}

	@Override
	public void run() {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(mqHostName);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(exchangeName, "topic");
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, exchangeName, routerKey);
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
			Integer actionType;
			String uid;
			Map<String, Object> headers;
			ConfigurationItem ci;
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				headers = delivery.getProperties().getHeaders();
				actionType = (Integer) headers.get("actionType");
				uid = (String) headers.get("uid");
				String message = new String(delivery.getBody());
				switch (actionType) {
				case 1:
					ci = JsonUtils.fromJson(message, ConfigurationItem.class);
					EntityRelationUtils.initialCIRelation(ci);
					baseService.addNew(ci);
					break;
				case 2:
					ci = JsonUtils.fromJson(message, ConfigurationItem.class);
					EntityRelationUtils.initialCIRelation(ci);
					baseService.update(ci);
				case 3:
					ci = new ConfigurationItem();
					ci.setUid(uid);
					ci.setRowStatus(RowStatus.invalid.value);
					baseService.logicDelete(ci);
					break;
				}
				if (LOGGER.isDebugEnabled()) {
					String routingKey = delivery.getEnvelope().getRoutingKey();
					LOGGER.debug(" [x] Received '" + routingKey + "':'" + "' \n headers: actionType=" + actionType + ", uid=" + uid + "\n body:" + message);
				}
			}
		} catch (Exception e) {
			LOGGER.error("consume " + mqHostName + " " + exchangeName + " erorr!", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(1);
		es.execute(new SubscribeCIsTopic());
		es.shutdown();
	}

}
