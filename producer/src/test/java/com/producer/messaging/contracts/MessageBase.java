package com.producer.messaging.contracts;

import com.producer.messaging.processor.AsciidocMessageProcessor;
import com.producer.messaging.service.Data;
import com.producer.messaging.service.MessagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMessageVerifier
public class MessageBase {
    @Autowired
    MessagingService messagingService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        AsciidocMessageProcessor asciidocMessageProcessor = new AsciidocMessageProcessor(testInfo.getDisplayName(), rabbitTemplate);
        rabbitTemplate.setRoutingKey("some.routing.key");
        rabbitTemplate.setBeforePublishPostProcessors(asciidocMessageProcessor);
    }

    protected void mockSendMessage() {
        Data data = new Data();
        data.setId("1234");
        data.setName("John");
        messagingService.send(data);
    }
}

