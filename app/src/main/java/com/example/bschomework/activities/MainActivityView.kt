package com.example.bschomework.activities

interface MainActivityView {
    fun savedToast()
    fun notSavedToast()
    fun hideAddMenuItem()
    fun showAddMenuItem()
    fun hideSaveMenuItem()
    fun showSaveMenuItem()
}