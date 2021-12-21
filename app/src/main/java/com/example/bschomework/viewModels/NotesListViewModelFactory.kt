package com.example.bschomework.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bschomework.room.NotesRepository

class NotesListViewModelFactory(private val repository: NotesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = NotesListViewModel(repository) as T

}