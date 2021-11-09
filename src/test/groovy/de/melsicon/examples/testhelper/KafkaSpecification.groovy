package de.melsicon.examples.testhelper


import io.micronaut.test.support.TestPropertyProvider
import spock.lang.Specification

class KafkaSpecification extends Specification implements TestPropertyProvider {

    static def kafka = new KafkaTestContainer()

    @Override
    Map<String, String> getProperties() {
        def properties = new HashMap<String, String>()
        properties.putAll(kafka.properties)
        return properties
    }
}
