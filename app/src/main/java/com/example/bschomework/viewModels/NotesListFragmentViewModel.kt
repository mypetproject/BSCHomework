package com.example.bschomework.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bschomework.App
import com.example.bschomework.room.NoteData

class NotesListFragmentViewModel : ViewModel() {
    private val db = App.instance.database

    val notes : LiveData<List<NoteData>> = db.noteDao().getAllNotes()
}
