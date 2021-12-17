package com.example.bschomework.fragments

import androidx.lifecycle.LifecycleCoroutineScope

interface CreateNoteFragmentView {
    fun savedToast()
    fun notSavedToast()
    fun getLifecycleScope(): LifecycleCoroutineScope
    fun showSaveMenuItem()
    fun hideSaveMenuItem()
    fun save()
}