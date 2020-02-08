package org.evanframework.rabbitmq;

import org.evanframework.rabbitmq.api.MessageType;
import org.evanframework.rabbitmq.api.RabbitMessage;
import org.evanframework.rabbitmq.producer.autoconfigure.RabbitProducerAutoConfiguration;
import org.evanframework.rabbitmq.producer.broker.ProducerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * $[SimpleMQProducerTest] 简单的发送测试
 *
 * @author ruanchaobo
 * @date 2020/2/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SimpleMQProducerTest.class})
@ComponentScan({"org.evanframework.rabbitmq.*"})
public class SimpleMQProducerTest {

    @Autowired
    private ProducerClient producerClient;

    @Test
    public void testProducerClient() throws Exception {
        for (int i = 0; i < 1; i++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            RabbitMessage message = new RabbitMessage(
                    uniqueId,
                    "delay-message",
                    "springboot.abc",
                    attributes,
                    0
            );

            message.setMessageType(MessageType.RELIANT);
//            message.setDelayMills(50000);
            producerClient.send(message, new RabbitTemplate.ConfirmCallback() {

                @Override
                public void confirm(CorrelationData correlationData, boolean b, String s) {
                    System.out.println("确认！！！");
                }
            }, new RabbitTemplate.ReturnCallback() {
                @Override
                public void returnedMessage(org.springframework.amqp.core.Message message, int i, String s, String s1, String s2) {
                    System.out.println("失败！！！");
                }
            });

            Thread.sleep(100000);
        }
    }
}
