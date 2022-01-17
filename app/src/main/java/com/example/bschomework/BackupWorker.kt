package com.example.bschomework

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bschomework.room.NotesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BackupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val notesRepository: NotesRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        notesRepository.allNotesSize().let {
            Log.d(
                TAG,
                this.applicationContext.resources.getQuantityString(R.plurals.notes_saved, it, it)
            )
        }

        return Result.success()
    }

    companion object {
        private const val TAG = "myLogs"
    }
}