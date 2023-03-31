package com.apache.pulsar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PulsarApplication

fun main(args: Array<String>) {
	runApplication<PulsarApplication>(*args)
}
