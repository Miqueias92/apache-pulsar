


package com.apache.pulsar.config

import org.apache.pulsar.client.api.PulsarClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PulsarConfiguration {

    private val pulsarUrl: String = "pulsar://localhost:6650"

    @Bean
    fun pulsarClient(): PulsarClient {
        return PulsarClient.builder()
                .serviceUrl(pulsarUrl)
                .build()
    }
}