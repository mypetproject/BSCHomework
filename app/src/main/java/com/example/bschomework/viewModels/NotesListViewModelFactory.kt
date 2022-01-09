package com.example.bschomework.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bschomework.room.NotesRepository
import javax.inject.Inject

class NotesListViewModelFactory @Inject constructor(private val repository: NotesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return NotesListViewModel(repository) as T
    }
}