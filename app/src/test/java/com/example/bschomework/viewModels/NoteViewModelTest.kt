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

    private lateinit var model: NoteViewModel
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

        model = NoteViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @After
    @Throws(IOException::class)
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun insertData() = runBlocking {

        model.header.value = "header"
        model.content.value = "note"
        model.saveData(-1L)

        var successSaved = false
        model.onSaveSuccessEvent.observeForever {
            successSaved = true
        }

        verify(noteDao).insert(any())

        assertTrue(successSaved)
    }

    @Test
    fun updateData() = runBlocking {

        model.header.value = "edited_header"
        model.content.value = "edited_note"
        model.saveData(1L)


        var successSaved = false
        model.onSaveSuccessEvent.observeForever {
            successSaved = true
        }

        verify(noteDao).update(any())

        assertTrue(successSaved)
    }

    @Test
    fun tryToSaveEmptyData() = runBlocking {

        model.header.value = ""
        model.content.value = ""
        model.saveData(-1L)

        var successSaved = false
        model.onSaveSuccessEvent.observeForever {
            successSaved = true
        }
        assertFalse(successSaved)

        var failSaving = false
        model.onSaveNotSuccessEvent.observeForever {
            failSaving = true
        }

        assertTrue(failSaving)
    }

    @Test
    fun setMenuItemVisibility() {
        model.header.value = "header"
        model.content.value = "note"
        model.setMenuItemsVisibility()

        var successSaved = false
        model.onShowMenuItemsEvent.observeForever {
            successSaved = true
        }
        assertTrue(successSaved)
    }

    @Test
    fun setMenuItemVisibilityEmptyHeader() {
        model.header.value = ""
        model.content.value = "note"
        model.setMenuItemsVisibility()

        var successShowMenuItems = false
        model.onShowMenuItemsEvent.observeForever {
            successShowMenuItems = true
        }
        assertFalse(successShowMenuItems)

        var failShowMenuItems = false
        model.onHideMenuItemsEvent.observeForever {
            failShowMenuItems = true
        }
        assertTrue(failShowMenuItems)
    }

    @Test
    fun setMenuItemVisibilityEmptyNote() {
        model.header.value = "header"
        model.content.value = ""
        model.setMenuItemsVisibility()

        var successShowMenuItems = false
        model.onShowMenuItemsEvent.observeForever {
            successShowMenuItems = true
        }
        assertFalse(successShowMenuItems)

        var failShowMenuItems = false
        model.onHideMenuItemsEvent.observeForever {
            failShowMenuItems = true
        }
        assertTrue(failShowMenuItems)
    }
}