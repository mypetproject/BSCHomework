package com.example.bschomework.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bschomework.room.NoteData
import com.example.bschomework.room.NotesDatabase
import com.example.bschomework.room.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class NoteViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var modelForInsertItem: NoteViewModel
    private lateinit var modelForEditItem: NoteViewModel
    private lateinit var db: NotesDatabase
    private lateinit var noteDataForInsert: NoteData
    private lateinit var noteDataForEdit: NoteData
    private lateinit var repository: NotesRepository

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()


    /*@Before
    fun setupDispatcher() {

    }*/

    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        /*db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()*/

        noteDataForInsert = NoteData("header", "note").apply { id = 1L }
        noteDataForEdit = NoteData("edited_header", "edited_note")

        // repository = mock(NotesRepository::class.java)

        repository = mock()/*<NotesRepository> {
            onBlocking {
                getNoteById(anyLong())
            } doReturn (noteDataForInsert)
        }*/


        modelForInsertItem = NoteViewModel(repository)
       // modelForEditItem = NoteViewModel(repository, noteDataForInsert.id)

    }
    @Test
    fun checkId() {
        assertTrue(modelForInsertItem.id < 0)
        assertTrue(modelForEditItem.id > 0)
    }

    @Test
    fun saveData() = runBlocking {

        modelForInsertItem.header.value = "header"
        modelForInsertItem.note.value = "note"
        modelForInsertItem.saveData()

        var successSaved = false
        modelForInsertItem.onSaveSuccessEvent.observeForever {
            successSaved = true
        }
        assertTrue(successSaved)
    }

    @Test
    fun updateData() = runBlocking {

        saveData()

        modelForEditItem.header.value = noteDataForEdit.header
        modelForEditItem.note.value = noteDataForEdit.note
        modelForEditItem.saveData()

        var successSaved = false
        modelForEditItem.onSaveSuccessEvent.observeForever {
            successSaved = true
        }
        assertTrue(successSaved)
    }

    @Test
    fun tryToSaveEmptyData() = runBlocking {

        modelForInsertItem.header.value = ""
        modelForInsertItem.note.value = ""
        modelForInsertItem.saveData()

        var successSaved = false
        modelForInsertItem.onSaveSuccessEvent.observeForever {
            successSaved = true
        }
        assertFalse(successSaved)

        var failSaving = false
        modelForInsertItem.onSaveNotSuccessEvent.observeForever {
            failSaving = true
        }
        assertTrue(failSaving)
    }

    @Test
    fun setMenuItemVisibility() {
        modelForInsertItem.header.value = "header"
        modelForInsertItem.note.value = "note"
        modelForInsertItem.setMenuItemsVisibility()

        var successSaved = false
        modelForInsertItem.onShowMenuItemsEvent.observeForever {
            successSaved = true
        }
        assertTrue(successSaved)
    }

    @Test
    fun setMenuItemVisibilityEmptyHeader() {
        modelForInsertItem.header.value = ""
        modelForInsertItem.note.value = "note"
        modelForInsertItem.setMenuItemsVisibility()

        var successShowMenuItems = false
        modelForInsertItem.onShowMenuItemsEvent.observeForever {
            successShowMenuItems = true
        }
        assertFalse(successShowMenuItems)

        var failShowMenuItems = false
        modelForInsertItem.onHideMenuItemsEvent.observeForever {
            failShowMenuItems = true
        }
        assertTrue(failShowMenuItems)
    }

    @Test
    fun setMenuItemVisibilityEmptyNote() {
        modelForInsertItem.header.value = "header"
        modelForInsertItem.note.value = ""
        modelForInsertItem.setMenuItemsVisibility()

        var successShowMenuItems = false
        modelForInsertItem.onShowMenuItemsEvent.observeForever {
            successShowMenuItems = true
        }
        assertFalse(successShowMenuItems)

        var failShowMenuItems = false
        modelForInsertItem.onHideMenuItemsEvent.observeForever {
            failShowMenuItems = true
        }
        assertTrue(failShowMenuItems)
    }
}