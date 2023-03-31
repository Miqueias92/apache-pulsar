package com.apache.pulsar.service.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.pulsar.client.api.*
import org.springframework.pulsar.annotation.PulsarListener
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.annotation.PostConstruct

@Component
class PulsarConsumer(
        private val pulsarClient: PulsarClient
) {

    private lateinit var consumer: Consumer<String>

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    @PostConstruct
    fun init() {
        consumer = pulsarClient.newConsumer(Schema.STRING)
                .topic("persistent://public/default/my-topic")
                .subscriptionName("my-subscription")
                .subscriptionType(SubscriptionType.Shared)
                .messageListener { consumer, msg ->
                    executorService.submit { consumeMessage(consumer, msg) }
                }
                .subscribe()
    }

    //@PulsarListener(topics = ["my-topic"], subscriptionName = "my-subscription")
    private fun consumeMessage(consumer: Consumer<String>, message: Message<String>) {
        try {
            val payload = message.data

            println("Teste de mensagem: ${String(payload)}")
            consumer.acknowledgeAsync(message)
        } catch (e: Exception) {
            consumer.negativeAcknowledge(message)
        }
    }
}