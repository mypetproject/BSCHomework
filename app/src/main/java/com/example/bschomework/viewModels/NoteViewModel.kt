package com.example.bschomework.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bschomework.arch.SingleLiveEvent
import com.example.bschomework.room.NoteData
import com.example.bschomework.room.NotesDatabase
import kotlinx.coroutines.launch

class NoteViewModel(private val db: NotesDatabase) : ViewModel() {

    val header: MutableLiveData<String> by lazy { MutableLiveData<String>("") }
    val note: MutableLiveData<String> by lazy { MutableLiveData<String>("") }

    var id = -1L

    val notes: LiveData<List<NoteData>> = db.noteDao().getAllNotes()

    suspend fun getNoteById(id: Long): NoteData? = db.noteDao().getNoteById(id)

    constructor(db: NotesDatabase, id: Long) : this(db) {
        this.id = id
        setData()
    }

    private fun setData() = viewModelScope.launch {
        db.noteDao().getNoteById(id).let {
            header.value = it?.header
            note.value = it?.note
        }
    }

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

    fun saveData() = viewModelScope.launch {
        if (checkData()) {

            db.noteDao().getNoteById(id)?.let {
                it.header = header.value.toString()
                it.note = note.value.toString()
                db.noteDao().update(it)
            } ?: db.noteDao().insert(NoteData(header.value.toString(), note.value.toString()))

            onSaveSuccessEvent.call()
        } else {
            onSaveNotSuccessEvent.call()
        }
    }

    private fun checkData(): Boolean {
        return header.value.toString().isNotEmpty() && note.value.toString().isNotEmpty()
    }
}