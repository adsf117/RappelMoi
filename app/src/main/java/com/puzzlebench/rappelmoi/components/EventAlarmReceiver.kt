package com.puzzlebench.rappelmoi.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.puzzlebench.rappelmoi.components.EventWorker.Companion.setEventWorkerData

class EventAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            intent?.getIntExtra(EXTRA_EVENT_ID, 0)?.let { eventId ->

                val workerConstraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true).build()

                val eventWorker = OneTimeWorkRequestBuilder<EventWorker>()
                    .setConstraints(workerConstraints)
                    .setInputData(setEventWorkerData(eventId))
                    .build()

                val workManager = WorkManager.getInstance(it)
                workManager.enqueue(eventWorker)
                Log.d(EventWorker::class.java.canonicalName, "AlarmReceiver for eventId : $eventId")
            }
        }
    }

    companion object {
        const val EVENT_ALARM_CODE = 1010
        const val EXTRA_EVENT_ID = "EXTRA_ID_THEM"
    }
}