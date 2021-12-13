package com.example.bschomework.fragments

import androidx.lifecycle.LifecycleCoroutineScope

interface NoteDataFragmentView {
    fun showButtons()
    fun hideButtons()
    fun savedToast()
    fun notSavedToast()
    fun getLifecycleScope(): LifecycleCoroutineScope
}