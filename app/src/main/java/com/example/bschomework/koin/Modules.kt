package com.example.bschomework.koin

import com.example.bschomework.arch.NoteInteractor
import com.example.bschomework.room.NotesDatabase
import com.example.bschomework.room.NotesRepository
import com.example.bschomework.viewModels.NoteViewModel
import com.example.bschomework.viewModels.NotesListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { NoteInteractor().getNote() }
}

val repositoryModule = module {
    single { NotesDatabase.getDatabase(androidContext()).noteDao() }
    single { NotesRepository(get()) }
}

val viewModelModule = module {

    viewModel {
        NoteViewModel(get())
    }

    viewModel {
        NotesListViewModel(get())
    }
}