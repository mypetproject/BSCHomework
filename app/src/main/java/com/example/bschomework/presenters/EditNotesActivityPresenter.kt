package com.example.bschomework.presenters

import android.content.Intent
import com.example.bschomework.App
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.EditNotesActivityView
import com.example.bschomework.room.NoteData
import kotlinx.coroutines.launch

class EditNotesActivityPresenter(private val view: EditNotesActivityView, intent: Intent) {
    private val db = App.instance.database
    var notes = mutableListOf<NoteData>()

    init {
        updateNotesList(intent)
    }

    private fun updateNotesList(intent: Intent) {
        view.getLifecycleScope().launch {
            notes.clear()
            notes.addAll(db.noteDao().getAllNotes())

            view.setPagerCurrentItem(
                notes.indexOf(
                    db.noteDao().getNoteById(intent.getLongExtra(EditNotesActivity.EXTRA_LONG, 0L))
                )
            )
        }
    }
}