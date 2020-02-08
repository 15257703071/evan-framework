package org.evanframework.rabbitmq.producer.broker;

import com.google.common.base.Preconditions;
import org.evanframework.rabbitmq.api.MessageProducer;
import org.evanframework.rabbitmq.api.MessageType;
import org.evanframework.rabbitmq.api.RabbitMessage;
import org.evanframework.rabbitmq.api.exception.MessageRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 	$ProducerClient 发送消息的实际实现类
 * @author ruanchaobo
 *
 */
@Component
public class ProducerClient implements MessageProducer {

	@Autowired
	private RabbitBroker rabbitBroker;

	/**
	 * 发送消息
	 * @param message
	 * @throws MessageRunTimeException
	 */
	@Override
	public void send(RabbitMessage message) throws MessageRunTimeException {

		Preconditions.checkNotNull(message.getTopic());
		String messageType = message.getMessageType();
		switch (messageType) {
			case MessageType.RAPID:
				rabbitBroker.rapidSend(message);
				break;
			case MessageType.CONFIRM:
				rabbitBroker.confirmSend(message);
				break;
			case MessageType.RELIANT:
				rabbitBroker.reliantSend(message);
				break;
		default:
			break;
		}
	}

	/**
	 * 批量发送
	 * 	$send Messagetype
	 */
	@Override
	public void send(List<RabbitMessage> messages) throws MessageRunTimeException {

		messages.forEach( rabbitMessage -> {
			rabbitMessage.setMessageType(MessageType.RAPID);
			MessageHolder.add(rabbitMessage);
		});
		rabbitBroker.sendMessages();
	}

}
