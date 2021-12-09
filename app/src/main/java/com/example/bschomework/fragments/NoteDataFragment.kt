package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bschomework.R
import com.example.bschomework.activities.EditNoteActivity
import com.example.bschomework.databinding.FragmentNoteDataBinding
import com.example.bschomework.presenters.NoteDataFragmentPresenter

class NoteDataFragment : Fragment(R.layout.fragment_note_data), NoteDataFragmentView {

    val presenter = NoteDataFragmentPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        FragmentNoteDataBinding.inflate(inflater, container, false).also {
            it.presenter = presenter
            return it.root
        }
    }

    override fun showShareButton() {
        (activity as EditNoteActivity).showShareButton()
    }

    override fun hideShareButton() {
        (activity as EditNoteActivity).hideShareButton()
    }

    override fun savedToast() {
        (activity as EditNoteActivity).savedToast()
    }

    override fun notSavedToast() {
        (activity as EditNoteActivity).notSavedToast()
    }
}