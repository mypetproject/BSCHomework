package com.example.bschomework.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bschomework.NoteData
import com.example.bschomework.NotesListAdapter
import com.example.bschomework.R
import com.example.bschomework.activities.EditNoteActivity
import com.example.bschomework.databinding.FragmentNotesListBinding
import com.example.bschomework.presenters.NotesListFragmentPresenter

class NotesListFragment : Fragment(R.layout.fragment_notes_list), NotesListFragmentView {

    val presenter = NotesListFragmentPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        FragmentNotesListBinding.inflate(inflater, container, false).run {

            notesRecyclerview.adapter =
                NotesListAdapter(presenter.notesModel.notes) { noteData: NoteData ->
                    presenter.onNotesListItemClick(noteData)
                }

            return root
        }
    }

    override fun notesListItemClicked(noteData: NoteData) {
        startActivity(Intent(this.context, EditNoteActivity::class.java).apply {
            putExtra(EditNoteActivity.REQUEST_KEY, noteData)
        })
    }
}