package com.example.bschomework.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteData::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}