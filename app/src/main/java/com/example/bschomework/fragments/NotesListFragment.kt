package com.example.bschomework.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.adapters.NotesListAdapter
import com.example.bschomework.databinding.FragmentNotesListBinding
import com.example.bschomework.presenters.NotesListFragmentPresenter
import com.example.bschomework.room.NoteData

class NotesListFragment : Fragment(R.layout.fragment_notes_list), NotesListFragmentView {

    val presenter = NotesListFragmentPresenter(this)

    private var adapter = NotesListAdapter(presenter.notes) { noteData: NoteData ->
        presenter.onNotesListItemClick(noteData)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        FragmentNotesListBinding.inflate(inflater, container, false).run {
            notesRecyclerview.adapter = adapter
            return root
        }
    }

    override fun onResume() {
        super.onResume()

        presenter.updateNotesList()

        (activity as MainActivity).run {
            showAddMenuItem()
            hideSaveMenuItem()
        }
    }

    override fun notesListItemClicked(noteData: NoteData) {

        startActivity(
            Intent(this.context, EditNotesActivity::class.java)
                .putExtra(EditNotesActivity.EXTRA_LONG, noteData.id)
        )
    }

    override fun getLifecycleScope(): LifecycleCoroutineScope = lifecycleScope

    override fun notifyAdapter() {
        adapter.notifyDataSetChanged()
    }
}