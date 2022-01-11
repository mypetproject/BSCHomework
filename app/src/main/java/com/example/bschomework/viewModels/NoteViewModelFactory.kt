package com.example.bschomework.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bschomework.room.NotesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class NoteViewModelFactory @AssistedInject constructor(
    private val repository: NotesRepository,
    @Assisted("noteId") val id: Long
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (id >= 0) NoteViewModel(repository, id) as T
        else NoteViewModel(repository) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("noteId") id: Long): NoteViewModelFactory
    }
}