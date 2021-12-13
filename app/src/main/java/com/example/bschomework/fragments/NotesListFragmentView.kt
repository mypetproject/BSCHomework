package com.example.bschomework.fragments

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.bschomework.room.NoteData

interface NotesListFragmentView {
    fun notesListItemClicked(noteData: NoteData)
    fun getLifecycleScope(): LifecycleCoroutineScope
    fun notifyAdapter()
}