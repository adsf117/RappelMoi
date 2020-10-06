package com.puzzlebench.rappelmoi.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            context.let {

                val workerConstraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true).build()

                val updateSeedDataBaseWorker = OneTimeWorkRequestBuilder<BootEventWorker>()
                    .setConstraints(workerConstraints)
                    .build()

                val workManager = WorkManager.getInstance(it)
                workManager.enqueue(updateSeedDataBaseWorker)

            }
        }
    }
}