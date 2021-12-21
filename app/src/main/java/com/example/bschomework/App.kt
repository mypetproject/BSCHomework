package com.example.bschomework

import android.app.Application
import com.example.bschomework.room.NotesDatabase
import com.example.bschomework.room.NotesRepository

class App : Application() {

    private val database by lazy { NotesDatabase.getDatabase(this) }
    val repository by lazy { NotesRepository(database) }
}