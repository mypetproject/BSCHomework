package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bschomework.NoteData
import com.example.bschomework.NotesListAdapter
import com.example.bschomework.R
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.databinding.FragmentNotesListBinding
import com.example.bschomework.presenters.NoteDataFragmentPresenter
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

        val bundle = Bundle().also {
            it.putParcelable(NoteDataFragmentPresenter.REQUEST_KEY, noteData)
        }

        (activity as MainActivity).supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_notes_list, NoteDataFragment().also {
                it.arguments = bundle
            })
            commit()
        }
    }
}