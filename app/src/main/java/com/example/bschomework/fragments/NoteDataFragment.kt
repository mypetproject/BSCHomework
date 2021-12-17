package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.databinding.FragmentNoteDataBinding
import com.example.bschomework.presenters.NoteDataFragmentPresenter


class NoteDataFragment : Fragment(R.layout.fragment_note_data), NoteDataFragmentView {

    lateinit var presenter: NoteDataFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        presenter = NoteDataFragmentPresenter(this, arguments)

        FragmentNoteDataBinding.inflate(inflater, container, false).also {
            it.presenter = presenter
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

    override fun save() {
        presenter.saveData()
    }

    override fun getTextForShare(): String = presenter.run {
        "$header\n$note"
    }

    companion object {
        const val NOTE_ID = "note_id"

        fun newInstance(id: Long) = NoteDataFragment().apply {
            arguments = Bundle().apply { putLong(NOTE_ID, id) }
        }
    }
}