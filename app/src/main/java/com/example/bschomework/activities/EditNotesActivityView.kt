package com.example.bschomework.activities

interface EditNotesActivityView {
    fun showButtons()
    fun hideButtons()
    fun savedToast()
    fun notSavedToast()
    fun setPagerCurrentItem(position: Int)
}