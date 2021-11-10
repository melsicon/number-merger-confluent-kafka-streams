package de.melsicon.examples.testhelper

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.protocol.types.Field.Str
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

class KafkaTestContainer {

    static final KafkaContainer kafkaContainer

    static {
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))
                .withEmbeddedZookeeper()
        kafkaContainer.start()
        def bootstrapServers = kafkaContainer.getBootstrapServers()

        createTopics(bootstrapServers)

    }

    static def createTopics(String bootstrapServers) {
        def adminClient = AdminClient
                .create(["bootstrap.servers": bootstrapServers])
        adminClient
                .createTopics(["random-number-1-v1", "merged-topic-v1"]
                        .collect { topic -> new NewTopic(topic, 1, 1 as short) })
    }

    static Map<String, String> getProperties() {
        def bootstrapServers = kafkaContainer.getBootstrapServers()
        Map<String, String> configs = new HashMap<>()
        configs.put("kafka.bootstrap.servers", bootstrapServers)
        configs.put("kafka.schema.registry.url", "mock://schemaregistry")
        return configs
    }
}
