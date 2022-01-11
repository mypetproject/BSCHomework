package com.example.bschomework.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.adapters.NotesListAdapter
import com.example.bschomework.databinding.FragmentNotesListBinding
import com.example.bschomework.room.NoteData
import com.example.bschomework.viewModels.NotesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private val model: NotesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNotesListBinding.inflate(inflater, container, false).apply {
            model.notes.observe(this@NotesListFragment, { notes ->
                notesRecyclerview.adapter =
                    NotesListAdapter(notes) { noteData: NoteData ->
                        notesListItemClicked(noteData)
                    }
            })
        }.root

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).run {
            showAddMenuItem()
            hideSaveMenuItem()
        }
    }

    private fun notesListItemClicked(noteData: NoteData) {
        startActivity(
            Intent(this.context, EditNotesActivity::class.java)
                .putExtra(EditNotesActivity.EXTRA_LONG, noteData.id)
        )
    }
}