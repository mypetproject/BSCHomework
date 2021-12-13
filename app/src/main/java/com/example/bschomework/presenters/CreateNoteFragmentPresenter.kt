package com.example.bschomework.presenters

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.bschomework.App
import com.example.bschomework.BR
import com.example.bschomework.fragments.CreateNoteFragmentView
import com.example.bschomework.room.NoteData
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class CreateNoteFragmentPresenter(private val view: CreateNoteFragmentView) : BaseObservable() {

    private val db = App.instance.database

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

    private fun setButtonsVisibility() {
        if (checkData()) {
            view.showSaveMenuItem()
        } else {
            view.hideSaveMenuItem()
        }
    }

    fun saveData() {
        if (checkData()) {

            view.getLifecycleScope().launch {
                db.noteDao().insert(NoteData(0, header, note))
            }

            view.savedToast()
        } else {
            view.notSavedToast()
        }
    }

    private fun checkData(): Boolean {
        return header.isNotEmpty() && note.isNotEmpty()
    }
}