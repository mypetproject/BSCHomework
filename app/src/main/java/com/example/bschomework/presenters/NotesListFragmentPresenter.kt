package com.example.bschomework.presenters

import com.example.bschomework.NoteData
import com.example.bschomework.NotesModel
import com.example.bschomework.fragments.NotesListFragmentView
import java.util.*

class NotesListFragmentPresenter(private val view: NotesListFragmentView) {

    var notesModel = NotesModel(mutableListOf())

    init {
        for (i in 1..50) {
            notesModel.notes.add(NoteData("Note $i", "Text $i", Date()))
        }
    }

    fun onNotesListItemClick(noteData: NoteData) {
        view.notesListItemClicked(noteData)
    }

}