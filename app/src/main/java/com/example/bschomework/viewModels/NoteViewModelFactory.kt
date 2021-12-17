package com.example.bschomework.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bschomework.room.NotesDatabase

class NoteViewModelFactory(private val db : NotesDatabase) :
    ViewModelProvider.NewInstanceFactory() {

    private var id = -1L

    constructor(db : NotesDatabase, id : Long) : this(db) {
        this.id = id
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (id >= 0) NoteViewModel(db, id) as T
        else NoteViewModel(db) as T
    }

}