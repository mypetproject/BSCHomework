package com.example.bschomework.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bschomework.App
import com.example.bschomework.arch.SingleLiveEvent
import kotlinx.coroutines.launch

class NoteDataFragmentViewModel(val id : Long) : ViewModel() {

    private val db = App.instance.database

    val header: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val note: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val onSaveSuccessEvent = SingleLiveEvent<Unit>()
    val onSaveNotSuccessEvent = SingleLiveEvent<Unit>()
    val onShowMenuItemsEvent = SingleLiveEvent<Unit>()
    val onHideMenuItemsEvent = SingleLiveEvent<Unit>()

    init {
        setData()
    }

    private fun setData() = viewModelScope.launch {
        db.noteDao().getNoteById(id).let {
            header.value = it.header
            note.value = it.note
        }
    }

    fun setMenuItemsVisibility() {
        if (checkData()) {
            onShowMenuItemsEvent.call()
        } else {
            onHideMenuItemsEvent.call()
        }
    }

    fun saveData() = viewModelScope.launch {
        if (checkData()) {

            db.noteDao().getNoteById(id).let {
                it.header = header.value.toString()
                it.note = note.value.toString()
                db.noteDao().update(it)
            }

            onSaveSuccessEvent.call()
        } else {
            onSaveNotSuccessEvent.call()
        }
    }

    private fun checkData(): Boolean {
        return header.value.toString().isNotEmpty() && note.value.toString().isNotEmpty()
    }
}

