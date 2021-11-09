package de.melsicon.examples.testhelper

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

class KafkaTestContainer {

    static final KafkaContainer kafkaContainer

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))
                .withEmbeddedZookeeper()
        kafkaContainer.start()

        createTopics(kafkaContainer)

    }

    private static void createTopics() {
        def adminClient = AdminClient
                .create(["bootstrap.servers": kafkaContainer.getBootstrapServers()])
        adminClient
                .createTopics(["random-number-1-v1", "merged-topic-v1"]
                        .collect { topic -> new NewTopic(topic, 1, 1 as short) })
    }

    static Map<String, String> getProperties() {
        return ["bootstrap.servers": kafkaContainer.getBootstrapServers(),
        "kafka.schema.registry.url": "mock://schemaregistry"]
    }
}
