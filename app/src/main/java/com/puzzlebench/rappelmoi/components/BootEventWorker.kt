package com.puzzlebench.rappelmoi.components

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.puzzlebench.rappelmoi.database.EvenDao
import com.puzzlebench.rappelmoi.database.Event
import com.puzzlebench.rappelmoi.database.RappelMoiDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BootEventWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val dataSource = RappelMoiDatabase.getInstance(this.applicationContext).evenDao
        getAll(dataSource)?.forEach {
            AlarmScheduler.scheduleAlarmsForReminder(this.applicationContext, it)
        }
        return Result.success()
    }

    private suspend fun getAll(dao: EvenDao): List<Event>? =
        withContext(Dispatchers.IO) {
            return@withContext dao.getAllFeatureEvents()
        }
}