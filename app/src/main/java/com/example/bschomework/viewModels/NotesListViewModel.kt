package com.example.bschomework.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bschomework.room.NoteData
import com.example.bschomework.room.NotesRepository

class NotesListViewModel(repository: NotesRepository) : ViewModel() {

    val notes: LiveData<List<NoteData>> = repository.allNotes

    var fragments: MutableMap<Int, Fragment> = mutableMapOf()
}