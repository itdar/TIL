package com.itdar.rabbitmqconsumer.describer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQHelper {

    public RabbitMQHelper() {

    }

//    @RabbitListener(bindings = @QueueBinding(
//        value = @Queue(value = "testQueue"),
//        exchange = @Exchange(value = "direct"),
//        key = "testQueue")
//    )
    @RabbitListener(queues = "testQueue")
    public void listen(final String message){

        System.out.println("Consuming : " + message);

    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(50);
        return factory;
    }

    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        return connectionFactory;
    }

}
