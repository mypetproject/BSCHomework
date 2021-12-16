package com.example.bschomework.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteData: NoteData): Long

    @Update
    suspend fun update(noteData: NoteData)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteData

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<NoteData>>
}