package de.melsicon.examples

import de.melsicon.examples.testhelper.KafkaSpecification
import de.melsicon.examples.testhelper.KafkaTestConsumer
import de.melsicon.examples.testhelper.KafkaTestProducer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject

import java.util.concurrent.TimeUnit

@MicronautTest
class MergerStreamTest extends KafkaSpecification {

    @Inject
    KafkaTestProducer kafkaTestProducer

    @Inject
    KafkaTestConsumer kafkaTestConsumer

    def setupSpec() {
        Thread.sleep(5000L)
    }

    def "sendAndReceive"() {
        given:
        var key = "k1"
        var value = "v1"

        when:
        kafkaTestProducer.sendSomething(key, value)

        then:
        def result = kafkaTestConsumer.messages.poll(1000L, TimeUnit.MILLISECONDS)
        result.first() == key
        result.second() == value

    }
}
