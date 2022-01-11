package com.example.bschomework.room

import androidx.lifecycle.LiveData
import javax.inject.Inject

open class NotesRepository @Inject constructor(private val dao: NoteDao) {

    val allNotes: LiveData<List<NoteData>> by lazy { dao.getAllNotes() }

    suspend fun insert(noteData: NoteData) {
        dao.insert(noteData)
    }

    suspend fun update(noteData: NoteData) {
        dao.update(noteData)
    }

    suspend fun getNoteById(id: Long): NoteData? = dao.getNoteById(id)
}