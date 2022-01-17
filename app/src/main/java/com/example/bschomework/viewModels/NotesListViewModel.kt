package com.example.bschomework.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bschomework.adapters.QueryFilter
import com.example.bschomework.room.NoteData
import com.example.bschomework.room.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(repository: NotesRepository) : ViewModel(),
    QueryFilter<NoteData> {

    val notes: LiveData<List<NoteData>> = repository.allNotes

    var fragments: MutableMap<Int, Fragment> = mutableMapOf()

    override fun filter(query: String): List<NoteData> =
        notes.value?.filter { it.header.lowercase().contains(query.lowercase()) }.orEmpty()
}