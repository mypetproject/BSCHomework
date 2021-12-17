package com.example.bschomework.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.databinding.FragmentEditNoteBinding
import com.example.bschomework.room.NotesDatabase
import com.example.bschomework.viewModels.NoteViewModel
import com.example.bschomework.viewModels.NoteViewModelFactory


class EditNoteFragment : Fragment(R.layout.fragment_edit_note), EditNoteFragmentView {

    val model: NoteViewModel by viewModels {
        NoteViewModelFactory(
            NotesDatabase.getDatabase(context as Context), arguments?.getLong(NOTE_ID) as Long
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEditNoteBinding.inflate(inflater, container, false).also {
        it.model = model
        it.lifecycleOwner = this
        subscribeToViewModel()
    }.root

    private fun subscribeToViewModel() {

        model.header.observe(this) {
            model.setMenuItemsVisibility()
        }

        model.note.observe(this) {
            model.setMenuItemsVisibility()
        }

        model.onSaveSuccessEvent.observe(this) {
            showToast(getString(R.string.saved))
        }

        model.onSaveNotSuccessEvent.observe(this) {
            showToast(getString(R.string.not_saved))
        }

        model.onShowMenuItemsEvent.observe(this) {
            (activity as EditNotesActivity).showButtons()
        }

        model.onHideMenuItemsEvent.observe(this) {
            (activity as EditNotesActivity).hideButtons()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun save() {
        model.saveData()
    }

    override fun getTextForShare(): String = model.run {
        "${header.value.toString()}\n${note.value.toString()}"
    }

    companion object {
        const val NOTE_ID = "note_id"

        fun newInstance(id: Long) = EditNoteFragment().apply {
            arguments = Bundle().apply { putLong(NOTE_ID, id) }
        }
    }
}