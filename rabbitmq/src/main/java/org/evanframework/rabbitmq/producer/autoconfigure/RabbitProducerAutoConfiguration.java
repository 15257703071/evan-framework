package org.evanframework.rabbitmq.producer.autoconfigure;

import org.evanframework.rabbitmq.producer.broker.ProducerClient;
import org.evanframework.task.annotation.EnableElasticJob;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 	$RabbitProducerAutoConfiguration 自动装配 
 * @author ruanchaobo
 *
 */
@EnableElasticJob
@Configuration
@ComponentScan({"org.evanframework.rabbitmq.*"
        ,"org.springframework.boot.autoconfigure.amqp"
})
public class RabbitProducerAutoConfiguration {

}
