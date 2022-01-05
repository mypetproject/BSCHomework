package com.example.bschomework.hilt

import com.example.bschomework.adapters.NotesListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class AdaptersModule {

    @Provides
    fun provideNotesListAdapter(): NotesListAdapter = NotesListAdapter(emptyList()){}
}