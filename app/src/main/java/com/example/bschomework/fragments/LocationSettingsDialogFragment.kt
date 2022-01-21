package com.example.bschomework.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.bschomework.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LocationSettingsDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            requireContext(),
            R.style.Theme_BSCHomework_MaterialAlertDialog
        )
            .setTitle(getString(R.string.question_location_settings))
            .setMessage(getString(R.string.message_location_settings_dialog))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                (parentFragment as NotesListFragment).locationSettingsAlertDialogOKButtonClicked()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .show()
    }

    companion object {
        const val TAG = "LocationSettingsDialog"
    }
}