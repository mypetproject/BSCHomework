package com.example.bschomework.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bschomework.arch.SingleLiveEvent
import com.example.bschomework.room.NoteData
import com.example.bschomework.room.NotesRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NotesRepository) : ViewModel() {

    val header: MutableLiveData<String> by lazy { MutableLiveData<String>("") }
    val note: MutableLiveData<String> by lazy { MutableLiveData<String>("") }

    var id = 0L

    val onSaveSuccessEvent = SingleLiveEvent<Unit>()
    val onSaveNotSuccessEvent = SingleLiveEvent<Unit>()
    val onShowMenuItemsEvent = SingleLiveEvent<Unit>()
    val onHideMenuItemsEvent = SingleLiveEvent<Unit>()

    constructor(repository: NotesRepository, id: Long) : this(repository) {
        this.id = id
        setData()
    }

    private fun setData() = viewModelScope.launch {
        repository.getNoteById(id).let {
            header.value = it?.header
            note.value = it?.note
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

            NoteData(header.value.toString(), note.value.toString()).let {
                if (id > 0) {
                    it.id = id
                    repository.update(it)
                } else repository.insert(it)
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