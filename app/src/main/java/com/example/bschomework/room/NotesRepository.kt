package com.example.bschomework.room

import androidx.lifecycle.LiveData

open class NotesRepository(private val db: NotesDatabase) {

    val allNotes : LiveData<List<NoteData>> = db.noteDao().getAllNotes()

    suspend fun insert(noteData: NoteData) {
        db.noteDao().insert(noteData)
    }

    suspend fun update(noteData: NoteData) {
        db.noteDao().update(noteData)
    }

    suspend fun getNoteById(id: Long): NoteData? =
        db.noteDao().getNoteById(id)
}