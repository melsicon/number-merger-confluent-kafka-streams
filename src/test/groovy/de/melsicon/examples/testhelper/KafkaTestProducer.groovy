package de.melsicon.examples.testhelper

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface KafkaTestProducer {
    @Topic("random-number-1-v1")
    void sendSomething(@KafkaKey String key, String value)
}
