package com.example.bschomework.activities

import androidx.lifecycle.LifecycleCoroutineScope

interface EditNotesActivityView {
    fun showButtons()
    fun hideButtons()
    fun savedToast()
    fun notSavedToast()
    fun getLifecycleScope(): LifecycleCoroutineScope
    fun setPagerCurrentItem(position: Int)
}