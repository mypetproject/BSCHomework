package com.example.bschomework.presenters

import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.bschomework.App
import com.example.bschomework.BR
import com.example.bschomework.fragments.NoteDataFragment
import com.example.bschomework.fragments.NoteDataFragmentView
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class NoteDataFragmentPresenter(private val view: NoteDataFragmentView, arguments: Bundle?) :
    BaseObservable() {

    private val db = App.instance.database

    var id = 0L

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

    init {
        id = arguments?.getLong(NoteDataFragment.NOTE_ID) as Long

        setData()
    }

    private fun setData() {
        view.getLifecycleScope().launch {
            db.noteDao().getNoteById(id).let {
                header = it.header
                note = it.note
            }
        }
    }

    private fun setButtonsVisibility() {

        if (checkData()) {
            view.showButtons()
        } else {
            view.hideButtons()
        }
    }

    fun saveData() {
        if (checkData()) {

            view.getLifecycleScope().launch {
                db.noteDao().getNoteById(id).let {
                    it.header = header
                    it.note = note
                    db.noteDao().update(it)
                }
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