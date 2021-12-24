package com.example.bschomework.arch

import com.example.bschomework.room.NoteData
import retrofit2.Call
import retrofit2.http.GET

interface NoteApi {

    @GET("note.json?alt=media")
    fun getNote(): Call<NoteData>
}