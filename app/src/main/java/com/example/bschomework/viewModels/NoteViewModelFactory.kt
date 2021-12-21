package com.example.bschomework.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bschomework.room.NotesRepository

class NoteViewModelFactory(private val repository: NotesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    private var id = -1L

    constructor(repository: NotesRepository, id : Long) : this(repository) {
        this.id = id
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (id >= 0) NoteViewModel(repository, id) as T
        else NoteViewModel(repository) as T
    }
}