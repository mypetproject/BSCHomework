package com.example.bschomework.presenters

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.bschomework.BR
import com.example.bschomework.NoteData
import com.example.bschomework.fragments.NoteDataFragmentView
import kotlin.properties.Delegates

class NoteDataFragmentPresenter(private val view: NoteDataFragmentView) : BaseObservable() {

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

    fun setData(noteData : NoteData) {
        header = noteData.header
        note = noteData.note
    }

    private fun setButtonsVisibility() {

        saveButtonEnabled = checkData().apply {

            if (this) {
                view.showShareButton()
            } else {
                view.hideShareButton()
            }
        }
    }

    fun saveData() {
        if (checkData()) {
            view.savedToast()
        } else {
            view.notSavedToast()
        }
    }

    private fun checkData(): Boolean {
        return header.isNotEmpty() && note.isNotEmpty()
    }
}