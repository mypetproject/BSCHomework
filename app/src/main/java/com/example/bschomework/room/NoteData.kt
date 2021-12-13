package com.example.bschomework.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteData(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "header") var header: String,
    @ColumnInfo(name = "note") var note: String
)