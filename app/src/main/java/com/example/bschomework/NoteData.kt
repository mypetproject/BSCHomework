package com.example.bschomework

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class NoteData(var header: String, var note: String, var date: Date) : Parcelable