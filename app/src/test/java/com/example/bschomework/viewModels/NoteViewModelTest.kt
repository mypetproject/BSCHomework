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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.io.IOException


@RunWith(JUnit4::class)
class NoteViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var modelForInsertItem: NoteViewModel
    private lateinit var modelForEditItem: NoteViewModel
    private lateinit var noteData: NoteData
    private lateinit var repository: NotesRepository
    private lateinit var noteDao: NoteDao

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        noteData = NoteData("header", "note")

        noteDao = mock {
            onBlocking { getNoteById(0L) } doReturn (null)
            onBlocking { getNoteById(1L) } doReturn (noteData)
        }

        repository = NotesRepository(noteDao)

        modelForInsertItem = NoteViewModel(repository)
        modelForEditItem = NoteViewModel(repository,1L)
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
        assertTrue(modelForInsertItem.id == 0L)
        assertTrue(modelForEditItem.id > 0L)
    }

    @Test
    fun insertData() = runBlocking {

        modelForInsertItem.header.value = "header"
        modelForInsertItem.note.value = "note"
        modelForInsertItem.saveData()

        var successSaved = false
        modelForInsertItem.onSaveSuccessEvent.observeForever {
            successSaved = true
        }

        verify(noteDao).insert(any())
        verify(noteDao).getNoteById(anyLong())

        assertTrue(successSaved)
    }

    @Test
    fun updateData() = runBlocking {

        modelForEditItem.header.value = "edited_header"
        modelForEditItem.note.value = "edited_note"
        modelForEditItem.saveData()


        var successSaved = false
        modelForEditItem.onSaveSuccessEvent.observeForever {
            successSaved = true
        }

        verify(noteDao).update(any())
        verify(noteDao).getNoteById(anyLong())

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

        verify(noteDao).getNoteById(anyLong())

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