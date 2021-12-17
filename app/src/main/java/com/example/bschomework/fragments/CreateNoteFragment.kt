package com.example.bschomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bschomework.R
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.databinding.FragmentCreateNoteBinding
import com.example.bschomework.viewModels.CreateNoteFragmentViewModel

class CreateNoteFragment : Fragment(R.layout.fragment_create_note), CreateNoteFragmentView {

    val model: CreateNoteFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCreateNoteBinding.inflate(inflater, container, false).apply {
        (activity as MainActivity).hideAddMenuItem()
        model = this@CreateNoteFragment.model
        subscribeToViewModel()
    }.root


    private fun subscribeToViewModel() {

        model.header.observe(this) {
            model.setMenuItemsVisibility()
        }

        model.note.observe(this) {
            model.setMenuItemsVisibility()
        }

        model.onSaveSuccessEvent.observe(this) {
            showToast(getString(R.string.saved))
        }

        model.onSaveNotSuccessEvent.observe(this) {
            showToast(getString(R.string.not_saved))
        }

        model.onShowMenuItemsEvent.observe(this) {
            (activity as MainActivity).showSaveMenuItem()
        }

        model.onHideMenuItemsEvent.observe(this) {
            (activity as MainActivity).hideSaveMenuItem()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun save() {
        model.saveData()
    }
}