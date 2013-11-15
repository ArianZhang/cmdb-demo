package com.xbrother.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(RabbitMQUtils.class);

	public static void sendTopic(String hostName, String exchangeName, String routerKey, BasicProperties properties, String message) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(hostName);

			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.exchangeDeclare(exchangeName, "topic");
			channel.basicPublish(exchangeName, routerKey, properties, message.getBytes());
		} catch (Exception e) {
			LOGGER.error("Sending topic to " + hostName + " failure!", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}
