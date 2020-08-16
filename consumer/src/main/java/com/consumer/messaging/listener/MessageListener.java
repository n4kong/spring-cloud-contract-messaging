package com.consumer.messaging.listener;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private Data data;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "consumer.queue"),
            exchange = @Exchange(value = "some.exchange"),
            key = "some.routing.key"
    ))
    public void handle(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }
}


