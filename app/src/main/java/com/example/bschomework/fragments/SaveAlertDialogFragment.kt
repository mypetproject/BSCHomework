package com.example.bschomework.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.bschomework.R
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.activities.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SaveAlertDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.question_save_note))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                when (activity) {
                    is MainActivity ->
                        (activity as MainActivity).saveAlertDialogOKButtonClicked()
                    is EditNotesActivity ->
                        (activity as EditNotesActivity).saveAlertDialogOKButtonClicked()
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .show()
    }

    companion object {
        const val TAG = "SaveAlertDialog"
    }
}