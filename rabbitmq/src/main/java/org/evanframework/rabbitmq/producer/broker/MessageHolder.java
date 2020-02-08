package org.evanframework.rabbitmq.producer.broker;

import com.google.common.collect.Lists;
import org.evanframework.rabbitmq.api.RabbitMessage;

import java.util.List;

public class MessageHolder {

	private List<RabbitMessage> messages = Lists.newArrayList();
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static final ThreadLocal<MessageHolder> holder = new ThreadLocal() {
		@Override
		protected Object initialValue() {
			return new MessageHolder();
		}
	};
	
	public static void add(RabbitMessage message) {
		holder.get().messages.add(message);
	}
	
	public static List<RabbitMessage> clear() {
		List<RabbitMessage> tmp = Lists.newArrayList(holder.get().messages);
		holder.remove();
		return tmp;
	}
	
}
