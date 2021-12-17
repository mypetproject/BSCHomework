package com.example.bschomework

import android.app.Application
import androidx.room.Room
import com.example.bschomework.room.NotesDatabase

class App : Application() {

    lateinit var database: NotesDatabase

    override fun onCreate() {
        super.onCreate()

        instance = this

        database = Room.databaseBuilder(this, NotesDatabase::class.java, "notes_database")
            .build()
    }

    companion object {

        @Volatile
        lateinit var instance: App
    }
}