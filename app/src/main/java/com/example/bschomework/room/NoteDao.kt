package com.example.bschomework.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(noteData: NoteData): Long

    @Update
    suspend fun update(noteData: NoteData)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteData>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteData
}