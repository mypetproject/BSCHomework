package com.example.bschomework.presenters

import com.example.bschomework.App
import com.example.bschomework.fragments.NotesListFragmentView
import com.example.bschomework.room.NoteData
import kotlinx.coroutines.launch

class NotesListFragmentPresenter(private val view: NotesListFragmentView) {

    private val db = App.instance.database
    var notes = mutableListOf<NoteData>()

    init {

        view.getLifecycleScope().launch {
            db.noteDao().getAllNotes().size.let {
                if (it < 30) for (i in it..30) db.noteDao()
                    .insert(NoteData(0, "Note $i", "Text $i"))
            }
            updateNotesList()
        }
    }

    fun updateNotesList() {
        view.getLifecycleScope().launch {
            notes.clear()
            notes.addAll(db.noteDao().getAllNotes())
            view.notifyAdapter()
        }
    }

    fun onNotesListItemClick(noteData: NoteData) {
        view.notesListItemClicked(noteData)
    }

}