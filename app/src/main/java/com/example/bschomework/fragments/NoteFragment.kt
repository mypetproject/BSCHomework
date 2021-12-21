package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bschomework.App
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.databinding.FragmentNoteBinding
import com.example.bschomework.viewModels.NoteViewModel
import com.example.bschomework.viewModels.NoteViewModelFactory

class NoteFragment : Fragment(R.layout.fragment_note), NoteFragmentView {

    private val model: NoteViewModel by viewModels {

        arguments?.run {
            NoteViewModelFactory(
                (activity?.application as App).repository,
                getLong(NOTE_ID)
            )
        } ?: NoteViewModelFactory(
                (activity?.application as App).repository
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentNoteBinding>(inflater,R.layout.fragment_note, container, false).also {
        it.model = model
        it.lifecycleOwner = this
        subscribeToViewModel()
        if (activity is MainActivity) (activity as MainActivity).hideAddMenuItem()
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
            when (activity) {
                is MainActivity ->
                    (activity as MainActivity).showSaveMenuItem()
                is EditNotesActivity ->
                    (activity as EditNotesActivity).showMenuItems()
            }
        }

        model.onHideMenuItemsEvent.observe(this) {
            when (activity) {
                is MainActivity ->
                    (activity as MainActivity).hideSaveMenuItem()
                is EditNotesActivity ->
                    (activity as EditNotesActivity).hideMenuItems()
            }
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

        fun newInstance(id: Long) = NoteFragment().apply {
            arguments = Bundle().apply { putLong(NOTE_ID, id) }
        }
    }
}