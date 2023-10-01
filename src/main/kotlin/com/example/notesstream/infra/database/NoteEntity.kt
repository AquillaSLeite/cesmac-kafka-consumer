package com.example.notesstream.infra.database

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "notes")
data class NoteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val title: String,
    val content: String,
    val done: Boolean
) {
    override fun toString(): String {
        return "{" +
                "id=$id, " +
                "title='$title', " +
                "content='$content', " +
                "done=$done" +
                "}"
    }
}
