package com.example.bschomework.dagger

import com.example.bschomework.arch.NoteApi
import com.example.bschomework.room.NoteData
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @Provides
    @Reusable
    fun provideCallNoteData(): Call<NoteData> = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        //TODO Стоит ли инжектить NoteApi и как, если стоит
        .build().create(NoteApi::class.java).getNote()

    companion object {
        private const val BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/course-3bf7b.appspot.com/o/"
    }
}