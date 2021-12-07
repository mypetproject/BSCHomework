package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.SimpleItemAnimator
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

        FragmentNotesListBinding.inflate(inflater, container, false).run {

            adapter = NotesListAdapter(presenter.notesModel.notes) { noteData: NoteData ->

                setFragmentResult(NoteDataFragment.REQUEST_KEY, bundleOf(NoteDataFragment.KEY to noteData))
            }

            notesRecyclerview.adapter = adapter

            (notesRecyclerview.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            return root
        }
    }

    override fun notifyAdapter() {
        adapter.notifyItemInserted(presenter.notesModel.notes.lastIndex)

    }
}