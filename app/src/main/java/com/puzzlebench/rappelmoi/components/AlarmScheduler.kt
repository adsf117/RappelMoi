
package com.puzzlebench.rappelmoi.components

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.puzzlebench.rappelmoi.database.Event

object AlarmScheduler {

    fun scheduleAlarmsForReminder(context: Context, event: Event) {

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = createPendingIntent(context, event)

        scheduleAlarm(event, alarmIntent, alarmMgr)
    }
}

private fun scheduleAlarm(
    event: Event,
    alarmIntent: PendingIntent?,
    alarmMgr: AlarmManager
) {
    alarmMgr.setExact(
        AlarmManager.RTC_WAKEUP,
        event.date,
        alarmIntent
    )
}


private fun createPendingIntent(context: Context, event: Event): PendingIntent? {
    val intent = Intent(context.applicationContext, EventAlarmReceiver::class.java).apply {
        action = event.description
        type = event.name
        putExtra(EventAlarmReceiver.EXTRA_EVENT_ID, event.id)
    }
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

