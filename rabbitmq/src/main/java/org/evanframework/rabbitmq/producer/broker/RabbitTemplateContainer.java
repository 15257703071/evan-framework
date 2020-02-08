package org.evanframework.rabbitmq.producer.broker;


import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.evanframework.rabbitmq.api.MessageType;
import org.evanframework.rabbitmq.api.RabbitMessage;
import org.evanframework.rabbitmq.api.exception.MessageRunTimeException;
import org.evanframework.rabbitmq.common.convert.GenericMessageConverter;
import org.evanframework.rabbitmq.common.convert.RabbitMessageConverter;
import org.evanframework.rabbitmq.common.serializer.Serializer;
import org.evanframework.rabbitmq.common.serializer.SerializerFactory;
import org.evanframework.rabbitmq.common.serializer.impl.JacksonSerializerFactory;
import org.evanframework.rabbitmq.producer.service.MessageStoreService;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 	$RabbitTemplateContainer池化封装
 * 	每一个topic 对应一个RabbitTemplate
 *	1.	提高发送的效率
 * 	2. 	可以根据不同的需求制定化不同的RabbitTemplate, 比如每一个topic 都有自己的routingKey规则
 * @author ruanchaobo
 */
@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

	private Map<String /* TOPIC */, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();
	
	private Splitter splitter = Splitter.on("#");
	
	private SerializerFactory serializerFactory = JacksonSerializerFactory.INSTANCE;
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private MessageStoreService messageStoreService;
	
	public RabbitTemplate getTemplate(RabbitMessage message) throws MessageRunTimeException {
		Preconditions.checkNotNull(message);
		String topic = message.getTopic();
		RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
		if(rabbitTemplate != null) {
			return rabbitTemplate;
		}
		log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);
		
		RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
		newTemplate.setExchange(topic);
		newTemplate.setRoutingKey(message.getRoutingKey());
		newTemplate.setRetryTemplate(new RetryTemplate());
		
		//	添加序列化反序列化和converter对象
		Serializer serializer = serializerFactory.create();
		GenericMessageConverter gmc = new GenericMessageConverter(serializer);
		RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
		newTemplate.setMessageConverter(rmc);
		
		String messageType = message.getMessageType();

		RabbitTemplate.ConfirmCallback confirmCallback = this;
		if (message.getConfirmCallback() != null) {
			confirmCallback = message.getConfirmCallback();
		}

		RabbitTemplate.ReturnCallback returnCallback = message.getReturnCallback();
		if (returnCallback != null) {
			newTemplate.setReturnCallback(returnCallback);
		}

		if(!MessageType.RAPID.equals(messageType)) {
			newTemplate.setConfirmCallback(confirmCallback);
		}
		
		rabbitMap.putIfAbsent(topic, newTemplate);
		
		return rabbitMap.get(topic);
	}

	/**
	 * 	无论是 confirm 消息 还是 reliant 消息 ，发送消息以后 broker都会去回调confirm
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		// 	具体的消息应答
		List<String> strings = splitter.splitToList(correlationData.getId());
		String messageId = strings.get(0);
		long sendTime = Long.parseLong(strings.get(1));
		String messageType = strings.get(2);
		if(ack) {
			//	当Broker 返回ACK成功时, 就是更新一下日志表里对应的消息发送状态为 SEND_OK
			
			// 	如果当前消息类型为reliant 我们就去数据库查找并进行更新
			if(MessageType.RELIANT.endsWith(messageType)) {
				this.messageStoreService.succuess(messageId);
			}
			log.info("send message is OK, confirm messageId: {}, sendTime: {}", messageId, sendTime);
		} else {
			log.error("send message is Fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
			
		}
	}
}
