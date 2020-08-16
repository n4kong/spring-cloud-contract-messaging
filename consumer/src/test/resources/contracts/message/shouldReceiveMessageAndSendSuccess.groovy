package com.producer.messaging.contracts;

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Producer receive and send message to destination queue'
    label 'receiveandsend.message.success'
    input {
        messageFrom('some.exchange.input')
        messageHeaders {
            header('contentType': 'application/json')
        }
        messageBody([
                name: $(anyNonEmptyString()),
                id: null
        ])
    }
    outputMessage {
        sentTo('some.exchange.output')
        headers {
            header('contentType': 'application/json')
            header('amqp_receivedRoutingKey': 'some.routing.key')
        }
        body([
                name: $(anyNonEmptyString()),
                id: $(anyNonEmptyString())
        ])
    }
}