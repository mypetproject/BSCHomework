package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.bschomework.MainActivityPresenter
import com.example.bschomework.NoteData
import com.example.bschomework.NotesListAdapter
import com.example.bschomework.R
import com.example.bschomework.databinding.FragmentNotesListBinding

class NotesListFragment : Fragment(R.layout.fragment_notes_list), NotesListFragmentView {

    private lateinit var adapter: NotesListAdapter

    lateinit var presenter : MainActivityPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        FragmentNotesListBinding.inflate(inflater, container, false).also {

            adapter = NotesListAdapter(presenter.notesModel.notes) { noteData: NoteData, position ->

                adapter.selectedPosition = position
                adapter.notifyDataSetChanged()

                setFragmentResult("dataForNoteDataFragment", bundleOf("noteData" to noteData))
            }

            it.notesRecyclerview.adapter = adapter

            return it.root
        }
    }

    override fun notifyAdapter() {
        adapter.notifyItemInserted(presenter.notesModel.notes.lastIndex)

    }
}