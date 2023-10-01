package com.example.notesstream.config

import com.example.notesstream.infra.database.NoteEntity
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConfig(
    @Value(value = "\${spring.kafka.consumer.bootstrap-servers}")
    private val bootstrapAddress: String
) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin? {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun noteTopic(): NewTopic? {
        return NewTopic("note-topic", 1, 1)
    }
}

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    @Value(value = "\${spring.kafka.consumer.bootstrap-servers}")
    private val bootstrapAddress: String,
    @Value(value = "\${spring.kafka.consumer.group-id}")
    private val groupId: String
) {

    @Bean
    fun noteKafkaConsumer(): ConcurrentKafkaListenerContainerFactory<String, NoteEntity> {
        val keyDeserializer = StringDeserializer()
        val valueDeserializer = JsonDeserializer(NoteEntity::class.java)

        val factory = ConcurrentKafkaListenerContainerFactory<String, NoteEntity>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(props(), keyDeserializer, valueDeserializer)
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        return factory
    }

    private fun props(): MutableMap<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId

        return props
    }
}
