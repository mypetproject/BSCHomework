package com.example.bschomework.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.adapters.NotesListAdapter
import com.example.bschomework.appComponent
import com.example.bschomework.databinding.FragmentNotesListBinding
import com.example.bschomework.room.NoteData
import com.example.bschomework.viewModels.NotesListViewModel
import javax.inject.Inject

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    @Inject
    lateinit var model: NotesListViewModel

    @Inject
    lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNotesListBinding.inflate(inflater, container, false).apply {

            //TODO Строку ниже лучше выполнять здесь или в onAttach?
            context?.appComponent?.inject(this@NotesListFragment)

            model.notes.observe(this@NotesListFragment, { notes ->
                notesRecyclerview.adapter =
                    adapter.also {
                        it.notes = notes
                        it.setOnItemClickListener { noteData: NoteData ->
                            notesListItemClicked(noteData)
                        }
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