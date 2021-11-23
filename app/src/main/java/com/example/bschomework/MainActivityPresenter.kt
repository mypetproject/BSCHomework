package com.example.bschomework

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlin.properties.Delegates

class MainActivityPresenter(private val view: MainActivityView) : BaseObservable(){

    @get:Bindable var header : String by Delegates.observable(""){ _, _, _ ->
        notifyPropertyChanged(BR.header)
        saveButtonEnabled = isSaveButtonEnabled()
    }

    @get:Bindable var note : String by Delegates.observable(""){ _, _, _ ->
        notifyPropertyChanged(BR.note)
        saveButtonEnabled = isSaveButtonEnabled()
    }

    @get:Bindable var saveButtonEnabled : Boolean by Delegates.observable(false){ _, _, _ ->
        notifyPropertyChanged(BR.saveButtonEnabled)
    }

    private var notesModel = NotesModel("", "")

    fun saveData() {

        notesModel.header = header
        notesModel.note = note

        if (checkData()) {
            view.savedToast()
        } else {
            view.notSavedToast()
        }
    }

    private fun checkData(): Boolean {
        return notesModel.header.isNotEmpty() && notesModel.note.isNotEmpty()
    }

    private fun isSaveButtonEnabled() : Boolean {
        return header.isNotEmpty() && note.isNotEmpty()
    }
}