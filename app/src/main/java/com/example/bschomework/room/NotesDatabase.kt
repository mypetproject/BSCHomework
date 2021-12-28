package com.example.bschomework.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [NoteData::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2, spec = NotesDatabase.OneToTwoAutoMigration::class)]
)

abstract class NotesDatabase : RoomDatabase() {
    @RenameColumn(tableName = "notes", fromColumnName = "note", toColumnName = "content")
    class OneToTwoAutoMigration : AutoMigrationSpec

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}