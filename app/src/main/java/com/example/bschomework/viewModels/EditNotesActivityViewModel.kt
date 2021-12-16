package com.example.bschomework.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bschomework.App
import com.example.bschomework.room.NoteData

class EditNotesActivityViewModel : ViewModel() {

    private val db = App.instance.database

    val notes : LiveData<List<NoteData>> = db.noteDao().getAllNotes()

    suspend fun getNoteById(id : Long) : NoteData = db.noteDao().getNoteById(id)

}