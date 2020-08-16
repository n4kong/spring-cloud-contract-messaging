package com.producer.messaging.contracts;

import com.producer.messaging.service.Data;
import com.producer.messaging.service.MessagingService;
import org.junit.jupiter.api.BeforeEach;
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

//    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    RabbitTemplate rabbitTemplate;

//    @Rule
//    public TestName testName = new TestName();

    @BeforeEach
    public void setup() {
//        String testName = getClass().getSimpleName() + "_" + this.testName.getMethodName();
//        AsciidocMessageProcessor asciidocMessageProcessor = new AsciidocMessageProcessor(testName, rabbitTemplate);
//        rabbitTemplate.addBeforePublishPostProcessors(asciidocMessageProcessor);
    }

    protected void mockSendMessage() {
//        rabbitTemplate.setRoutingKey("some.routing.key");
        Data data = new Data();
        data.setId("1234");
        data.setName("John");
        messagingService.send(data);
    }
}

