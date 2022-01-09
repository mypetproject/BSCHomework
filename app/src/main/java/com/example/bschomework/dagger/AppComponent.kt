package com.example.bschomework.dagger

import android.content.Context
import com.example.bschomework.activities.EditNotesActivity
import com.example.bschomework.fragments.NoteFragment
import com.example.bschomework.fragments.NotesListFragment
import com.example.bschomework.viewModels.NoteViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {

    fun inject(noteViewModel: NoteViewModel)

    fun inject(editNotesActivity: EditNotesActivity)
    fun inject(notesListFragment: NotesListFragment)
    fun inject(noteFragment: NoteFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}