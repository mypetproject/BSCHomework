package com.example.bschomework.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bschomework.App
import com.example.bschomework.room.NoteData
import kotlinx.coroutines.launch

class CreateNoteFragmentViewModel : ViewModel() {

    private val db = App.instance.database

    val header : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val note : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    /*@get:Bindable
    var header: String by Delegates.observable("") { _, _, _ ->
        notifyPropertyChanged(BR.header)
        setButtonsVisibility()
    }

    @get:Bindable
    var note: String by Delegates.observable("") { _, _, _ ->
        notifyPropertyChanged(BR.note)
        setButtonsVisibility()
    }

    private fun setButtonsVisibility() {
        if (checkData()) {
            view.showSaveMenuItem()
        } else {
            view.hideSaveMenuItem()
        }
    }*/

    fun saveData() = viewModelScope.launch{
        if (checkData()) {

            /*view.getLifecycleScope().launch {
                db.noteDao().insert(NoteData(header, note))
            }

            view.savedToast()*/
                db.noteDao().insert(NoteData(header.value as String, note.value as String))

            Log.d(TAG, "saved: ${header.value}, ${note.value}")
        } else {
            //view.notSavedToast()
            Log.d(TAG, "not saved: ${header.value}, ${note.value}")
        }
    }

    private fun checkData(): Boolean {
        return header.value.toString().isNotEmpty() && note.value.toString().isNotEmpty()
    }

    companion object {
        const val TAG = "myLogs"
    }
}