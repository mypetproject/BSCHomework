package com.example.bschomework.dagger

import android.content.Context
import androidx.room.Room
import com.example.bschomework.adapters.NotesListAdapter
import com.example.bschomework.room.NoteDao
import com.example.bschomework.room.NotesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    //TODO Приемлемо или лучше создавать бд в NotesDatabase?
    @Provides
    @Singleton
    fun provideNotesDatabase(context: Context): NotesDatabase = Room.databaseBuilder(
        context.applicationContext,
        NotesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideNoteDao(database: NotesDatabase): NoteDao = database.noteDao()

    @Provides
    fun provideAdapter(): NotesListAdapter = NotesListAdapter(emptyList()) {}

    companion object {
        private const val DATABASE_NAME = "notes_database"
    }
}