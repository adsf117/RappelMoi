package com.puzzlebench.rappelmoi.components

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.puzzlebench.rappelmoi.database.EvenDao
import com.puzzlebench.rappelmoi.database.Event
import com.puzzlebench.rappelmoi.database.RappelMoiDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        val eventId = inputData.getInt(EXTRA_EVENT_ID, 0)
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        val event = Event(eventId, "TestEvent Notification", "TestEvent Notification", 0L)
        notificationManager.showNotification(applicationContext, event)

        val dataSource = RappelMoiDatabase.getInstance(this.applicationContext).evenDao
        getEventBy(eventId, dataSource)?.let {
            notificationManager.showNotification(applicationContext, it)

        }

        Log.d(
            EventWorker::class.java.canonicalName,
            "get Info for eventId : $eventId"
        )
        return Result.success()
    }

    private suspend fun getEventBy(eventId: Int, dao: EvenDao): Event? =
        withContext(Dispatchers.IO) {
            return@withContext dao.getEventBy(eventId)
        }

    companion object {
        const val EXTRA_EVENT_ID = "EXTRA_EVENT_ID"
        fun setEventWorkerData(eventId: Int): Data = workDataOf(EXTRA_EVENT_ID to eventId)
    }
}