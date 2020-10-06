package com.puzzlebench.rappelmoi.components

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.puzzlebench.rappelmoi.R
import com.puzzlebench.rappelmoi.database.Event
import com.puzzlebench.rappelmoi.form.FormReminderActivity

// Notification ID.
private val NOTIFICATION_ID = 1

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.showNotification(applicationContext: Context, event: Event) {

    val contentIntent = Intent(applicationContext, FormReminderActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.event_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_baseline_alarm_on)
        .setContentTitle(event.name)
        .setContentText(event.description)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(false)
        .setPriority(NotificationCompat.PRIORITY_LOW)
    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
