package com.example.bschomework.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bschomework.arch.NoteApi
import com.example.bschomework.arch.SingleLiveEvent
import com.example.bschomework.room.NoteData
import com.example.bschomework.room.NotesRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NoteViewModel(private val repository: NotesRepository) : ViewModel() {

    val header: MutableLiveData<String> by lazy { MutableLiveData<String>("") }
    val content: MutableLiveData<String> by lazy { MutableLiveData<String>("") }

    val onSaveSuccessEvent = SingleLiveEvent<Unit>()
    val onSaveNotSuccessEvent = SingleLiveEvent<Unit>()
    val onShowMenuItemsEvent = SingleLiveEvent<Unit>()
    val onHideMenuItemsEvent = SingleLiveEvent<Unit>()
    val onShowProgressIndicatorEvent = SingleLiveEvent<Unit>()
    val onHideProgressIndicatorEvent = SingleLiveEvent<Unit>()
    val onFailProgressIndicatorEvent = SingleLiveEvent<Unit>()

    @Inject
    lateinit var noteApi: NoteApi

    constructor(repository: NotesRepository, id: Long) : this(repository) {
        setData(id)
    }

    private fun setData(id: Long) = viewModelScope.launch {
        repository.getNoteById(id).let {
            header.value = it?.header
            content.value = it?.content
        }
    }

    fun setMenuItemsVisibility() {
        if (checkData()) {
            onShowMenuItemsEvent.call()
        } else {
            onHideMenuItemsEvent.call()
        }
    }

    fun saveData(id : Long) = viewModelScope.launch {
        if (checkData()) {

            NoteData(header.value.toString(), content.value.toString()).let {
                if (id > 0) {
                    it.id = id
                    repository.update(it)
                } else repository.insert(it)
            }

            onSaveSuccessEvent.call()

        } else {
            onSaveNotSuccessEvent.call()
        }
    }

    private fun checkData(): Boolean {
        return header.value.toString().isNotEmpty() && content.value.toString().isNotEmpty()
    }

    fun downloadNoteButtonClick() {

        onShowProgressIndicatorEvent.call()

        noteApi.getNote().enqueue(object : Callback<NoteData> {
            override fun onResponse(call: Call<NoteData>, response: Response<NoteData>) {

                onHideProgressIndicatorEvent.call()

                header.value = response.body()?.header.orEmpty()
                content.value = response.body()?.content.orEmpty()
            }

            override fun onFailure(call: Call<NoteData>, t: Throwable) {
                onFailProgressIndicatorEvent.call()
            }
        })
    }
}