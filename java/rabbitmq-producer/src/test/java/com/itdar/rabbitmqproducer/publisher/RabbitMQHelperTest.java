package com.itdar.rabbitmqproducer.publisher;

import org.junit.jupiter.api.Test;

class RabbitMQHelperTest {

    @Test
    void sendRabbitQueueTest() {
        RabbitMQHelper rabbitMQHelper = new RabbitMQHelper();

        rabbitMQHelper.sendPushMessage("test queue message 1");
    }

}