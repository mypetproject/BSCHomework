package com.example.bschomework.presenters

import android.content.Intent
import com.example.bschomework.App
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.EditNotesActivityView
import com.example.bschomework.room.NoteData

class EditNotesActivityPresenter(val view: EditNotesActivityView) {
    private val db = App.instance.database


    suspend fun getNotes(): List<NoteData> {
        return db.noteDao().getAllNotes()
    }

    private suspend fun getNoteById(id: Long): NoteData {
        return db.noteDao().getNoteById(id)
    }

    suspend fun setPagerCurrentItem(intent: Intent) {
        view.setPagerCurrentItem(
            getNotes().indexOf(
                getNoteById(
                    intent.getLongExtra(
                        EditNotesActivity.EXTRA_LONG,
                        0L
                    )
                )
            )
        )
    }
}