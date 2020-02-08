package org.evanframework.rabbitmq.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 	$Message
 * @author ruanchaobo
 *
 */
@Data
public class RabbitMessage implements Serializable {

	private static final long serialVersionUID = 841277940410721237L;

	/* 	消息的唯一ID	*/
	private String messageId;
	
	/*	消息的主题		*/
	private String topic;
	
	/*	消息的路由规则	*/
	private String routingKey = "";
	
	/*	消息的附加属性	*/
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	/*	延迟消息的参数配置	*/
	private int delayMills;
	
	/*	消息类型：默认为confirm消息类型	*/
	private String messageType = MessageType.CONFIRM;

	/**
	 * 异步confirm,如果有应答会回调
	 */
	private RabbitTemplate.ConfirmCallback confirmCallback;

	/**
	 * 当方法被调用的时候，说明消息别rabbitmq broken拒接接收了
	 * 再有几种情况下回拒绝接收：没找到对应的exchange，对应的routingKey没有找到，没有找到对应的queue等情况。
	 */
	private RabbitTemplate.ReturnCallback returnCallback;


	public RabbitMessage() {
	}
	
	public RabbitMessage(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills) {
		this.messageId = messageId;
		this.topic = topic;
		this.routingKey = routingKey;
		this.attributes = attributes;
		this.delayMills = delayMills;
	}
	
	public RabbitMessage(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills,
						 String messageType) {
		this.messageId = messageId;
		this.topic = topic;
		this.routingKey = routingKey;
		this.attributes = attributes;
		this.delayMills = delayMills;
		this.messageType = messageType;
	}
	
}
