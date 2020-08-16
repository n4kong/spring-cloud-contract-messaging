package com.producer.messaging.contracts;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Producer send example success message to destination queue'
    label 'send.message.success'
    input {
        triggeredBy('mockSendMessage()')
    }

    outputMessage {
        sentTo 'some.exchange'
        headers {
            header('contentType': 'application/json')
            header('amqp_receivedRoutingKey': 'some.routing.key')
        }
        body([
                id  : $(anyNumber()),
                name: $(anyNonBlankString())
        ])
    }
}