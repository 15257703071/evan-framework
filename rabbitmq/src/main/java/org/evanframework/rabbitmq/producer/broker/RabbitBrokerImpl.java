package org.evanframework.rabbitmq.producer.broker;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.evanframework.rabbitmq.api.MessageType;
import org.evanframework.rabbitmq.api.RabbitMessage;
import org.evanframework.rabbitmq.producer.constant.BrokerMessageConst;
import org.evanframework.rabbitmq.producer.constant.BrokerMessageStatus;
import org.evanframework.rabbitmq.producer.entity.BrokerMessage;
import org.evanframework.rabbitmq.producer.service.MessageStoreService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 	$RabbitBrokerImpl 真正的发送不同类型的消息实现类
 * @author ruanchaobo
 *
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

	@Autowired
	private RabbitTemplateContainer rabbitTemplateContainer;
	
	@Autowired
	private MessageStoreService messageStoreService;
	
	@Override
	public void reliantSend(RabbitMessage message) {
		message.setMessageType(MessageType.RELIANT);
		BrokerMessage bm = messageStoreService.selectByMessageId(message.getMessageId());
		if(bm == null) {
			//1. 把数据库的消息发送日志先记录好
			Date now = new Date();
			BrokerMessage brokerMessage = new BrokerMessage();
			brokerMessage.setMessageId(message.getMessageId());
			brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
			//tryCount 在最开始发送的时候不需要进行设置
			brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
			brokerMessage.setCreateTime(now);
			brokerMessage.setUpdateTime(now);

			//回调方法不入库
			RabbitMessage copyMessage = new RabbitMessage();
			BeanUtils.copyProperties(message, copyMessage);
			copyMessage.setConfirmCallback(null);
			copyMessage.setReturnCallback(null);

			brokerMessage.setMessage(copyMessage);
			messageStoreService.insert(brokerMessage);			
		}
		//2. 执行真正的发送消息逻辑
		sendKernel(message);
	}
	
	/**
	 * 	$rapidSend迅速发消息
	 */
	@Override
	public void rapidSend(RabbitMessage message) {
		message.setMessageType(MessageType.RAPID);
		sendKernel(message);
	}
	
	/**
	 * 	$sendKernel 发送消息的核心方法 使用异步线程池进行发送消息
	 * @param message
	 */
	private void sendKernel(RabbitMessage message) {
		AsyncBaseQueue.submit((Runnable) () -> {
			CorrelationData correlationData =
					new CorrelationData(String.format("%s#%s#%s",//格式如：messageId#当前时间#messageType
							message.getMessageId(),
							System.currentTimeMillis(),
							message.getMessageType()));
			String topic = message.getTopic();
			String routingKey = message.getRoutingKey();
			RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
			rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
			log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());			
		});
	}

	@Override
	public void confirmSend(RabbitMessage message) {
		message.setMessageType(MessageType.CONFIRM);
		sendKernel(message);
	}

	@Override
	public void sendMessages() {
		List<RabbitMessage> messages = MessageHolder.clear();
		messages.forEach(message -> {
			MessageHolderAyncQueue.submit((Runnable) () -> {
				CorrelationData correlationData = 
						new CorrelationData(String.format("%s#%s#%s",
								message.getMessageId(),
								System.currentTimeMillis(),
								message.getMessageType()));
				String topic = message.getTopic();
				String routingKey = message.getRoutingKey();
				RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
				rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
				log.info("#RabbitBrokerImpl.sendMessages# send to rabbitmq, messageId: {}", message.getMessageId());			
			});			
		});
	}

}
