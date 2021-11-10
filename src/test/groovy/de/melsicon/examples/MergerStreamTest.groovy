package de.melsicon.examples

import de.melsicon.examples.testhelper.KafkaSpecification
import de.melsicon.examples.testhelper.KafkaTestConsumer
import de.melsicon.examples.testhelper.KafkaTestProducer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject

import java.util.concurrent.TimeUnit

import static java.util.concurrent.TimeUnit.SECONDS
import static org.awaitility.Awaitility.await

@MicronautTest
class MergerStreamTest extends KafkaSpecification {

    @Inject
    KafkaTestProducer kafkaTestProducer

    @Inject
    KafkaTestConsumer kafkaTestConsumer


    def "sendAndReceive"() {
        given:
        var key = "k1"
        var value = "v1"

        when:
        kafkaTestProducer.sendSomething(key, value)

        then:
        await().atMost(10, SECONDS).until(() -> !kafkaTestConsumer.messages.isEmpty());
        def result = kafkaTestConsumer.messages.first
        result == value

    }
}
