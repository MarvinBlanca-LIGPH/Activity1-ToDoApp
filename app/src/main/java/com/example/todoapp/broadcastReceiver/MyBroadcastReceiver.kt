package com.example.todoapp.broadcastReceiver

import android.app.PendingIntent
import android.content.*
import androidx.core.app.*
import com.example.todoapp.*

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val channelId = intent?.getStringArrayListExtra("channelId")
        val message = intent?.getStringArrayListExtra("message")
        val id = intent?.getIntegerArrayListExtra("id")

        channelId?.size?.let { size ->
            for (i in 0 until size) {
                val pendingIntent =
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, MainActivity::class.java),
                        0
                    )
                val notification = NotificationCompat.Builder(context, channelId[i])
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentTitle(context.resources.getString(R.string.notification_title))
                    .setContentText(message?.get(i))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

                val notificationManager = NotificationManagerCompat.from(context)
                id?.get(i)?.let { notificationManager.notify(it, notification.build()) }
            }
        }
    }
}