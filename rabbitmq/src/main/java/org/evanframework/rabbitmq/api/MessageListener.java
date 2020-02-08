package org.evanframework.rabbitmq.api;

/**
 * 	$MessageListener 消费者监听消息
 * @author ruanchaobo
 *
 */
public interface MessageListener {

	void onMessage(RabbitMessage message);
	
}
