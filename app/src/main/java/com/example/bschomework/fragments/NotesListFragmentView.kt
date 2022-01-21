package com.example.bschomework.fragments

interface NotesListFragmentView {
    fun filter(newText: String)
    fun getLocation()
    fun locationSettingsAlertDialogOKButtonClicked()
}