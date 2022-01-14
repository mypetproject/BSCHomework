package com.example.bschomework.hilt

import android.content.Context
import androidx.room.Room
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import com.example.bschomework.BackupWorker
import com.example.bschomework.room.NoteDao
import com.example.bschomework.room.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDao(@ApplicationContext context: Context): NoteDao = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        NOTES_DATABASE_NAME
    ).build().noteDao()

    @Provides
    fun provideBackupWorkRequest(): WorkRequest =
        OneTimeWorkRequestBuilder<BackupWorker>()
            .build()

    companion object {
        private const val NOTES_DATABASE_NAME = "notes_database"
    }
}