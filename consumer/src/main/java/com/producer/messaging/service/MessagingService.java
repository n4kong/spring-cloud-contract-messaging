package com.producer.messaging.service;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Component
public class MessagingService {




    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(Data data) {
        rabbitTemplate.convertAndSend("some.exchange", "some.routing.key", data);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "some.queue.input"),
            exchange = @Exchange(value = "some.exchange.input", ignoreDeclarationExceptions = "true")))
    public void handle(Data data) {
        data.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("some.exchange.output", "some.routing.key", data);
    }
}

