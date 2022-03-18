package com.itdar.rabbitmqproducer.publisher;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQHelper {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQHelper() {
        this.rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
    }

    public ConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("gino-vhost");

        return connectionFactory;
    }

    public void sendPushMessage(String message) {
//        rabbitTemplate.convertAndSend("amp.fanout", "testQueue", message);
        rabbitTemplate.convertAndSend("testQueue", message);
    }

}
