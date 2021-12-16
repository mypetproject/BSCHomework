package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.databinding.FragmentNoteDataBinding
import com.example.bschomework.viewModels.NoteDataFragmentViewModel
import com.example.bschomework.viewModels.NoteDataFragmentViewModelFactory


class NoteDataFragment : Fragment(R.layout.fragment_note_data), NoteDataFragmentView {

    val model: NoteDataFragmentViewModel by viewModels { NoteDataFragmentViewModelFactory(arguments?.getLong(NOTE_ID) as Long) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        FragmentNoteDataBinding.inflate(inflater, container, false).also {
            it.model = model
            it.lifecycleOwner = this
            return it.root
        }
    }

    override fun showButtons() {
        (activity as EditNotesActivity).showButtons()
    }

    override fun hideButtons() {
        (activity as EditNotesActivity).hideButtons()
    }

    override fun savedToast() {
        (activity as EditNotesActivity).savedToast()
    }

    override fun notSavedToast() {
        (activity as EditNotesActivity).notSavedToast()
    }

    override fun getLifecycleScope(): LifecycleCoroutineScope = lifecycleScope

    companion object {
        const val NOTE_ID = "note_id"

        fun newInstance(id: Long) = NoteDataFragment().apply {
            arguments = Bundle().apply { putLong(NOTE_ID, id) }
        }
    }
}