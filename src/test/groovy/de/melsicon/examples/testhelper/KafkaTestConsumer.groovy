package de.melsicon.examples.testhelper

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import org.spockframework.util.Pair

import java.util.concurrent.LinkedBlockingDeque

@Singleton
@KafkaListener(groupId = "testconsumer", offsetReset = OffsetReset.EARLIEST)
class KafkaTestConsumer {
    def messages = new LinkedBlockingDeque<Pair<String, String>>()

    @Topic("merged-topic-v1")
    receive(@KafkaKey String key, String value) {
        messages.add(key, value)
    }
}
