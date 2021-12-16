package com.example.bschomework.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bschomework.App
import kotlinx.coroutines.launch

class NoteDataFragmentViewModel(val id : Long) : ViewModel() {

    private val db = App.instance.database

    val header: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val note: MutableLiveData<String> by lazy { MutableLiveData<String>() }

   // val id: Long by lazy { arguments?.getLong(NoteDataFragment.NOTE_ID) as Long }


    init {
        setData()
    }

    private fun setData() = viewModelScope.launch {
        db.noteDao().getNoteById(id).let {
            header.value = it.header
            note.value = it.note
        }
    }

    fun saveData() = viewModelScope.launch {
        if (checkData()) {

            /*
            view.savedToast()*/
            db.noteDao().getNoteById(id).let {
                it.header = header.value.toString()
                it.note = note.value.toString()
                db.noteDao().update(it)
                Log.d(TAG, "db.noteDao().update(it) || it: $it ")
            }
        } else {
            // view.notSavedToast()
        }
    }

    private fun checkData(): Boolean {
        return header.value.toString().isNotEmpty() && note.value.toString().isNotEmpty()
    }

    companion object {
        const val TAG = "myLogs"
    }

}

