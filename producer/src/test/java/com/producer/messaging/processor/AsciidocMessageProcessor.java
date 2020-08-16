package com.producer.messaging.processor;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.PrettyPrintingContentModifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class AsciidocMessageProcessor implements MessagePostProcessor {

    private final String testName;
    private RabbitTemplate rabbitTemplate;

    public AsciidocMessageProcessor(String testName, RabbitTemplate rabbitTemplate) {
        this.testName = testName;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        try {
            File yourFile = new File("target/generated-snippets/" + testName + "/request-body.adoc");
            yourFile.getParentFile().mkdirs();
            yourFile.createNewFile();
            FileOutputStream oFile = new FileOutputStream(yourFile, false);
            PrintWriter printWriter = new PrintWriter(oFile);
            printWriter.println("[source,options=\"nowrap\"]");
            printWriter.println("----");
            String contentType = message.getMessageProperties().getContentType();
            printWriter.println("Content-Type: " + contentType);
            message.getMessageProperties().getHeaders().entrySet().stream()
                    .filter(c -> !c.getKey().startsWith("__"))
                    .forEach(c-> printWriter.println(c.getKey() + ": " + c.getValue()));
            Optional.ofNullable(rabbitTemplate.getRoutingKey())
                    .ifPresent(c -> printWriter.println("amqp_receivedRoutingKey: " + c));
            String body = jsonPretty(message.getBody(), contentType);
            printWriter.println("");
            printWriter.println(body);
            printWriter.println("----");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    private String jsonPretty(byte[] body, String contentType){
        byte[] jsonPretty = body;
        if (MediaType.parseMediaType(contentType).equals(MediaType.APPLICATION_JSON)) {
            PrettyPrintingContentModifier pretty = new PrettyPrintingContentModifier();
            jsonPretty = pretty.modifyContent(body, MediaType.APPLICATION_JSON);
        }
        return new String(jsonPretty);
    }

}