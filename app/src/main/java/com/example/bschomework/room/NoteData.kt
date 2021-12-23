package com.example.bschomework.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteData(
    @ColumnInfo(name = "header") val header: String,
    @ColumnInfo(name = "note") val note: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}