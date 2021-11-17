package com.example.bschomework

class MainActivityPresenter(private val view: View) {

    private var notesModel = NotesModel("", "")

    fun saveData(header: String, note: String) {

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

    interface View {
        fun savedToast()
        fun notSavedToast()
    }
}