package com.example.bschomework.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [NoteData::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(
        from = 1,
        to = 2,
        spec = NotesDatabase.OneToTwoAutoMigration::class
    )]
)

abstract class NotesDatabase : RoomDatabase() {
    @RenameColumn(tableName = "notes", fromColumnName = "note", toColumnName = "content")
    class OneToTwoAutoMigration : AutoMigrationSpec

    abstract fun noteDao(): NoteDao
}