package com.apache.pulsar.resource

import com.apache.pulsar.service.consumer.PulsarConsumer
import com.apache.pulsar.service.producer.PulsarProducer
import org.apache.pulsar.client.api.PulsarClientException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pulsar")
class PulsarResource {

    @Autowired
    lateinit var pulsarProducer: PulsarProducer

    @Autowired
    lateinit var pulsarConsumer: PulsarConsumer

    @PostMapping
    fun publishMessage(@RequestBody message: String): ResponseEntity<String> {
        try {
            pulsarProducer.produce("persistent://public/default/my-topic", message)
            return ResponseEntity.ok("Message published to topic: persistent://public/default/my-topic")
        } catch (ex: PulsarClientException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error publishing message: ${ex.message}")
        }
    }
}