package com.example.bschomework.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bschomework.App
import com.example.bschomework.arch.SingleLiveEvent
import com.example.bschomework.room.NoteData
import kotlinx.coroutines.launch

class CreateNoteFragmentViewModel : ViewModel() {

    var db = App.instance.database

    val header: MutableLiveData<String> by lazy { MutableLiveData<String>("")}

    val note: MutableLiveData<String> by lazy { MutableLiveData<String>("")}

    val onSaveSuccessEvent = SingleLiveEvent<Unit>()
    val onSaveNotSuccessEvent = SingleLiveEvent<Unit>()
    val onShowMenuItemsEvent = SingleLiveEvent<Unit>()
    val onHideMenuItemsEvent = SingleLiveEvent<Unit>()

    fun setMenuItemsVisibility() {
        if (checkData()) {
            onShowMenuItemsEvent.call()
        } else {
            onHideMenuItemsEvent.call()
        }
    }

    fun saveData() = viewModelScope.launch{
        if (checkData()) {
            db.noteDao().insert(NoteData(header.value.toString(), note.value.toString()))
            onSaveSuccessEvent.call()
        } else {
            onSaveNotSuccessEvent.call()
        }
    }

    private fun checkData(): Boolean {
        return header.value.toString().isNotEmpty() && note.value.toString().isNotEmpty()
    }
}