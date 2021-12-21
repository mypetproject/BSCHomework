package com.example.bschomework.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bschomework.room.NoteDao
import com.example.bschomework.room.NoteData
import com.example.bschomework.room.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.io.IOException


@RunWith(JUnit4::class)
class NoteViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var modelForInsertItem: NoteViewModel
    private lateinit var modelForEditItem: NoteViewModel
    private lateinit var noteDataForInsert: NoteData
    private lateinit var noteDataForEdit: NoteData
    private lateinit var repository: NotesRepository
    private lateinit var noteDao: NoteDao

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        noteDataForInsert = NoteData("header", "note").apply { id = 1L }
        noteDataForEdit = NoteData("edited_header", "edited_note").apply { id = 1L }

        noteDao = mock {
            onBlocking { getNoteById(anyLong()) } doReturn (noteDataForInsert)
        }

        repository = NotesRepository(noteDao)

        modelForInsertItem = NoteViewModel(repository)
        modelForEditItem = NoteViewModel(repository, noteDataForEdit.id)
    }

    @ExperimentalCoroutinesApi
    @After
    @Throws(IOException::class)
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
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

        assertEquals(noteDataForInsert, repository.getNoteById(noteDataForInsert.id))
    }

    @Test
    fun updateData() = runBlocking {

        modelForInsertItem.header.value = "header"
        modelForInsertItem.note.value = "note"
        modelForInsertItem.saveData()

        verify(noteDao, times(2)).getNoteById(anyLong())

        modelForEditItem.header.value = noteDataForEdit.header
        modelForEditItem.note.value = noteDataForEdit.note
        modelForEditItem.saveData()

        verify(noteDao, times(3)).getNoteById(anyLong())

        var successSaved = false
        modelForEditItem.onSaveSuccessEvent.observeForever {
            successSaved = true
        }
        assertTrue(successSaved)

        assertEquals(noteDataForEdit, repository.getNoteById(noteDataForEdit.id))
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