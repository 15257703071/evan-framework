package org.evanframework.rabbitmq.producer.broker;


import org.evanframework.rabbitmq.api.RabbitMessage;

/**
 * 	$RabbitBroker 具体发送不同种类型消息的接口
 * @author ruanchaobo
 *
 */
public interface RabbitBroker {

	/**
	 * 迅速消息发送
	 */
	void rapidSend(RabbitMessage message);

	/**
	 * 确认消息发送
	 */
	void confirmSend(RabbitMessage message);

	/**
	 * 可靠性消息发送
	 */
	void reliantSend(RabbitMessage message);

	/**
	 * 批量发送消息
	 */
	void sendMessages();
	
}
