package org.evanframework.rabbitmq.common.convert;

import com.google.common.base.Preconditions;
import org.evanframework.rabbitmq.api.RabbitMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * 	$RabbitMessageConverter
 * @author ruanchaobo
 *
 */
public class RabbitMessageConverter implements MessageConverter {

	private GenericMessageConverter delegate;
	
//	private final String delaultExprie = String.valueOf(24 * 60 * 60 * 1000);
	
	public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
		Preconditions.checkNotNull(genericMessageConverter);
		this.delegate = genericMessageConverter;
	}
	
	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
//		messageProperties.setExpiration(delaultExprie);
		RabbitMessage message = (RabbitMessage)object;
		messageProperties.setDelay(message.getDelayMills());
		return this.delegate.toMessage(object, messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		RabbitMessage msg = (RabbitMessage) this.delegate.fromMessage(message);
		return msg;
	}

}
