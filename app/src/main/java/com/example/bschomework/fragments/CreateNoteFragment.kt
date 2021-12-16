package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bschomework.R
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.databinding.FragmentCreateNoteBinding
import com.example.bschomework.presenters.CreateNoteFragmentPresenter
import com.example.bschomework.viewModels.CreateNoteFragmentViewModel

class CreateNoteFragment : Fragment(R.layout.fragment_create_note), CreateNoteFragmentView {

    lateinit var presenter: CreateNoteFragmentPresenter

    val model : CreateNoteFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as MainActivity).hideAddMenuItem()


        FragmentCreateNoteBinding.inflate(inflater, container, false).run {
            presenter = CreateNoteFragmentPresenter(this@CreateNoteFragment).also {
                this@CreateNoteFragment.presenter = it
            }
            model = this@CreateNoteFragment.model
            return root
        }
    }

    override fun savedToast() {
        (activity as MainActivity).savedToast()
    }

    override fun notSavedToast() {
        (activity as MainActivity).notSavedToast()
    }

    override fun getLifecycleScope() = lifecycleScope

    override fun showSaveMenuItem() {
        (activity as MainActivity).showSaveMenuItem()
    }

    override fun hideSaveMenuItem() {
        (activity as MainActivity).hideSaveMenuItem()
    }
}