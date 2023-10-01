package com.example.notesstream.consumer

import com.example.notesstream.infra.database.NoteEntity
import com.example.notesstream.infra.database.repository.NoteRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class NoteConsumer(
    private val noteRepository: NoteRepository
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = ["note-topic"], containerFactory = "noteKafkaConsumer")
    fun createdNotesConsumer(consumerRecord: ConsumerRecord<String, NoteEntity>, ack: Acknowledgment) {
        val initialTime = System.currentTimeMillis()
        try {
            noteRepository.save(consumerRecord.value())
            ack.acknowledge()

            logger.info("action=consume-event topic=${consumerRecord.topic()} partition=${consumerRecord.partition()} offset=${consumerRecord.offset()} key=${consumerRecord.key()} value=${consumerRecord.value()} executionTime=${System.currentTimeMillis() - initialTime}")
        } catch (e: Exception) {
            logger.error("action=consume-event topic=${consumerRecord.topic()} partition=${consumerRecord.partition()} offset=${consumerRecord.offset()} key=${consumerRecord.key()} value=${consumerRecord.value()} errorMessage=${e.message} executionTime=${System.currentTimeMillis() - initialTime}")
        }
    }
}
