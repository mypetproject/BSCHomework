package com.example.bschomework

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.bschomework.fragments.NotesListFragmentView
import java.util.*
import kotlin.properties.Delegates

class MainActivityPresenter(
    private val view: MainActivityView,
    private val fragmentView: NotesListFragmentView
) : BaseObservable() {

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

    var notesModel = NotesModel(mutableListOf())

    fun setButtonsVisibility() {

        isButtonsEnabled().let {
            saveButtonEnabled = it

            if (it) {
                view.showShareButton()
            } else {
                view.hideShareButton()
            }
        }
    }

    fun saveData() {

        notesModel.notes.add(NoteData(header, note, Date()))

        if (checkData()) {
            view.savedToast()
            fragmentView.notifyAdapter()
        } else {
            view.notSavedToast()
        }
    }

    private fun checkData(): Boolean {
        return header.isNotEmpty() && note.isNotEmpty()
    }

    private fun isButtonsEnabled(): Boolean {
        return header.isNotEmpty() && note.isNotEmpty()
    }

    fun photoButtonClicked() {
        view.photoButtonClicked()
    }
}