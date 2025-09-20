package com.labexam3.dilmith

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Create the notification channel (it's safe to call this multiple times)
        NotificationHelper.createNotificationChannel(context)
        // Show the notification
        NotificationHelper.showNotification(context)
    }
}