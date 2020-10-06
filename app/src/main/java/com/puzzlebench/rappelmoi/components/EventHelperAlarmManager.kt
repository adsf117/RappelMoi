package com.puzzlebench.rappelmoi.components

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.puzzlebench.rappelmoi.components.EventAlarmReceiver.Companion.EVENT_ALARM_CODE
import com.puzzlebench.rappelmoi.database.Event

class EventHelperAlarmManager constructor(private val context: Context) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private lateinit var intent: PendingIntent

    fun setEventAlarm(event: Event) {
        intent = Intent(context, EventAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                EVENT_ALARM_CODE,
                intent.putExtra(EventAlarmReceiver.EXTRA_EVENT_ID, event.id),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                event.date,
                intent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                event.date,
                intent
            )
        }
        Log.d(
            EventHelperAlarmManager::class.java.canonicalName,
            "init  Alarm for event ${event.name}"
        )
    }

    fun cancelAlarm() {
        if (::intent.isInitialized) {
            alarmManager.cancel(intent)
            Log.d(
                EventHelperAlarmManager::class.java.canonicalName,
                " Alarms cancelled "
            )
        }

    }
}