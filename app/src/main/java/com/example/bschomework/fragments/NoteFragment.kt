package com.example.bschomework.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.MainActivity
import com.example.bschomework.databinding.FragmentNoteBinding
import com.example.bschomework.viewModels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.fragment_note), NoteFragmentView {

    private val model: NoteViewModel by viewModels()

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentNoteBinding>(
        inflater,
        R.layout.fragment_note,
        container,
        false
    ).also {

        //TODO Как правильно передать id в NoteViewModel?
        arguments?.run {
            model.setData(getLong(NOTE_ID))
        }

        it.model = model
        it.lifecycleOwner = this
        subscribeToViewModel()
        if (activity is MainActivity) (activity as MainActivity).hideAddMenuItem()
        binding = it
    }.root

    private fun subscribeToViewModel() {

        model.header.observe(this) {
            model.setMenuItemsVisibility()
        }

        model.content.observe(this) {
            model.setMenuItemsVisibility()
        }

        model.onSaveSuccessEvent.observe(this) {
            showToast(getString(R.string.saved))
            activity?.sendBroadcast(Intent().apply {
                action = ACTION
                putExtra(NOTE_HEADER, model.header.value.toString())
                putExtra(NOTE_CONTENT, model.content.value.toString())
            })
        }

        model.onSaveNotSuccessEvent.observe(this) {
            showToast(getString(R.string.not_saved))
        }

        model.onShowMenuItemsEvent.observe(this) {
            when (activity) {
                is MainActivity ->
                    (activity as MainActivity).showSaveMenuItem()
                is EditNotesActivity ->
                    (activity as EditNotesActivity).showMenuItems()
            }
        }

        model.onHideMenuItemsEvent.observe(this) {
            when (activity) {
                is MainActivity ->
                    (activity as MainActivity).hideSaveMenuItem()
                is EditNotesActivity ->
                    (activity as EditNotesActivity).hideMenuItems()
            }
        }

        model.onShowProgressIndicatorEvent.observe(this) {
            binding.progressCircular.show()
        }

        model.onHideProgressIndicatorEvent.observe(this) {
            binding.progressCircular.hide()
            showToast(getString(R.string.downloaded))
        }

        model.onFailProgressIndicatorEvent.observe(this) {
            binding.progressCircular.hide()
            showToast(getString(R.string.download_failed))
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun save() {
        model.saveData()
    }

    override fun getTextForShare(): String = model.run {
        "${header.value.toString()}\n${content.value.toString()}"
    }

    companion object {
        const val NOTE_ID = "note_id"

        fun newInstance(id: Long) = NoteFragment().apply {
            arguments = Bundle().apply { putLong(NOTE_ID, id) }
        }

        private const val ACTION = "com.example.bschomework.action_saving"
        private const val NOTE_HEADER = "note_header"
        private const val NOTE_CONTENT = "note_content"
    }
}