package com.apache.pulsar.service.producer

import org.apache.pulsar.client.api.Producer
import org.springframework.stereotype.Component
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import java.util.concurrent.TimeUnit

@Component
class PulsarProducer(
        private val pulsarClient: PulsarClient
) {

    fun produce(topic: String = "persistent://public/default/my-topic", message: String) {
        val producer: Producer<String> = pulsarClient.newProducer(Schema.STRING)
                .topic(topic)
                .create()

        producer.newMessage().value(message).deliverAfter(10, TimeUnit.SECONDS).send()
    }

}