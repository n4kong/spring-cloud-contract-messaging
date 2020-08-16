package com.consumer.messaging.listener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureStubRunner
public class MessageListenerTest {
    @Autowired
    StubTrigger stubTrigger;

    @Autowired
    MessageListener messageListener;

    @Test
    public void shouldReceiveData() {
        stubTrigger.trigger("send.message.success");

        assertNotNull(messageListener.getData().getName());
        assertNotNull(messageListener.getData().getId());
    }
}
