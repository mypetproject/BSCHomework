package com.example.bschomework.fragments

import com.example.bschomework.room.NoteData

interface NotesListFragmentView {
    fun notesListItemClicked(noteData: NoteData)
}