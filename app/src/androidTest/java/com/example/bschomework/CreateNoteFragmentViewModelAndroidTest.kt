package com.example.bschomework

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bschomework.viewModels.CreateNoteFragmentViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class CreateNoteFragmentViewModelAndroidTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var model : CreateNoteFragmentViewModel

    @Before
    fun setUp() {

       mock(App::class.java)
        model = CreateNoteFragmentViewModel()
    }

    @Test
    fun saveData() {
        model.header.value = "header"
        model.note.value = "note"
        model.saveData()
        var successSaved = false
        model.onSaveSuccessEvent.observeForever{
            successSaved = true
            assertTrue(successSaved)
        }

    }

    @Test
    fun tryToSaveEmptyData() {
        model.header.value = ""
        model.note.value = ""
        model.saveData()
        var successSaved = false
        model.onSaveSuccessEvent.observeForever{
            successSaved = true
        }
        assertFalse(successSaved)
        var emptyContentCalled = false
        model.onSaveNotSuccessEvent.observeForever{
            emptyContentCalled = true
            assertTrue(emptyContentCalled)
        }

    }

}