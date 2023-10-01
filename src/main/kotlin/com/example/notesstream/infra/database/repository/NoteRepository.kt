package com.example.notesstream.infra.database.repository

import com.example.notesstream.infra.database.NoteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<NoteEntity, Int>
