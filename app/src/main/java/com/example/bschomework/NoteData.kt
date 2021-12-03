package com.example.bschomework

import java.io.Serializable
import java.util.*

data class NoteData(var header: String, var note: String, var date: Date) : Serializable