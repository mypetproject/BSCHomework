package com.example.bschomework.fragments

import com.example.bschomework.NoteData

interface NotesListFragmentView {
    fun notesListItemClicked(noteData : NoteData)
}