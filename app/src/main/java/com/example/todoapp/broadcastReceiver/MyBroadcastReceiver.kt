package com.example.todoapp.broadcastReceiver

import android.app.PendingIntent
import android.content.*
import androidx.core.app.*
import com.example.todoapp.*

class MyBroadcastReceiver : BroadcastReceiver() {
    private val channelId = "Channel 1"
    override fun onReceive(context: Context, intent: Intent?) {
        val pendingIntent =
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("TITLE")
            .setContentText("TEXT")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, notification.build())
    }
}