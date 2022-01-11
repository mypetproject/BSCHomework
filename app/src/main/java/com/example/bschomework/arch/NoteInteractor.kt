package com.example.bschomework.arch

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteInteractor {

    fun getNote(): NoteApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build().create(NoteApi::class.java)

    companion object {
        const val BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/course-3bf7b.appspot.com/o/"
    }
}