package com.example.bschomework.hilt

import com.example.bschomework.arch.NoteApi
import com.example.bschomework.room.NoteData
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ViewModelComponent::class)
@Module
class RetrofitModule {

    //TODO Приемлемо ли добавлять инициализацию бд и ретрофит в модуль или лучше использовать NotesDatabase и NoteInteractor?
    @Provides
    fun provideCallNoteData(): Call<NoteData> = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build().create(NoteApi::class.java).getNote()

    companion object {

        private const val BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/course-3bf7b.appspot.com/o/"
    }
}