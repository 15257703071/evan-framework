package org.evanframework.rabbitmq.api;

import java.util.List;

import org.evanframework.rabbitmq.api.exception.MessageRunTimeException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 	$MessageProducer
 * @author ruanchaobo
 *
 */
public interface MessageProducer {

	/**
	 * $send 发送消息，可根据自定义ConfirmCallback 和 ReturnCallback
	 * @param message
	 * @param confirmCallback
	 * @param returnCallback
	 * @throws MessageRunTimeException
	 */
	default void send(RabbitMessage message, RabbitTemplate.ConfirmCallback confirmCallback, RabbitTemplate.ReturnCallback returnCallback) throws MessageRunTimeException {
		message.setConfirmCallback(confirmCallback);
		message.setReturnCallback(returnCallback);
		send(message);
	}

	/**
	 * $send 发送消息，可根据自定义returnCallback
	 * @param message
	 * @param returnCallback
	 */
	default void send(RabbitMessage message, RabbitTemplate.ReturnCallback returnCallback) {
		message.setReturnCallback(returnCallback);
		send(message);
	}

	/**
	 * 	$send消息的发送
	 * @param message
	 * @throws MessageRunTimeException
	 */
	void send(RabbitMessage message) throws MessageRunTimeException;

	/**
	 * 	$send 消息的批量发送
	 * @param messages
	 * @throws MessageRunTimeException
	 */
	void send(List<RabbitMessage> messages) throws MessageRunTimeException;



	
}
