package org.evanframework.rabbitmq.common.serializer.impl;


import org.evanframework.rabbitmq.api.RabbitMessage;
import org.evanframework.rabbitmq.common.serializer.Serializer;
import org.evanframework.rabbitmq.common.serializer.SerializerFactory;

public class JacksonSerializerFactory implements SerializerFactory {

	public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();
	
	@Override
	public Serializer create() {
		return JacksonSerializer.createParametricType(RabbitMessage.class);
	}

}
