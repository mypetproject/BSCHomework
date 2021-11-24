package com.example.bschomework

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlin.properties.Delegates

class MainActivityPresenter(private val view: MainActivityView) : BaseObservable() {

    @get:Bindable
    var header: String by Delegates.observable("") { _, _, _ ->
        notifyPropertyChanged(BR.header)
        setButtonsVisibility()
    }

    @get:Bindable
    var note: String by Delegates.observable("") { _, _, _ ->
        notifyPropertyChanged(BR.note)
        setButtonsVisibility()
    }

    @get:Bindable
    var saveButtonEnabled: Boolean by Delegates.observable(false) { _, _, _ ->
        notifyPropertyChanged(BR.saveButtonEnabled)
    }

    private var notesModel = NotesModel(header, note)

    fun setButtonsVisibility() {
        val isButtonEnabled = isButtonsEnabled()

        saveButtonEnabled = isButtonEnabled

        if (isButtonEnabled) {
            view.showShareButton()
        } else {
            view.hideShareButton()
        }
    }

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

    private fun isButtonsEnabled(): Boolean {
        return header.isNotEmpty() && note.isNotEmpty()
    }

    fun photoButtonClicked() {
        view.photoButtonClicked()
    }
}