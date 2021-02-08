package com.example.todoapp.util

import android.app.*
import android.content.*
import android.graphics.*
import android.os.Build
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.broadcastReceiver.MyBroadcastReceiver
import java.io.ByteArrayOutputStream

object AppUtil {

    fun hideKeyboard(activity: Activity) {
        try {
            val inputManager: InputMethodManager = activity
                .getSystemService(Application.INPUT_METHOD_SERVICE) as InputMethodManager
            val currentFocusedView: View? = activity.currentFocus
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createNotificationChannel(activity: Activity, channelName: String, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val description = "Channel to notify user's tasks"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = description

            val notificationManager = activity.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun callNotification(
        context: Context?,
        channelIdArray: ArrayList<String>,
        messageArray: ArrayList<String>,
        idArray: ArrayList<Int?>,
        durationArray: ArrayList<Int>
    ) {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.putStringArrayListExtra("channelId", channelIdArray)
        intent.putStringArrayListExtra("message", messageArray)
        intent.putIntegerArrayListExtra("id", idArray)

        var requestCode = 0
        idArray.forEach { id ->
            id?.let { requestCode = it }
        }

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        val alarmManager =
            context?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val currTime: Long = System.currentTimeMillis()
        var time = 10000
        durationArray.forEach { time = it }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            currTime + time, pendingIntent
        )

        idArray.forEach { id ->
            id?.let { RealmUtil.notificationTriggered(it) }
        }
    }

    fun createAlertDialog(
        context: Context,
        title: String,
        message: String,
        negativeButtonText: String,
        positiveButtonText: String,
        negativeButtonClicked: (() -> Unit),
        positiveButtonClicked: (() -> Unit),
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(negativeButtonText) { _, _ ->
                negativeButtonClicked.invoke()
            }
            .setPositiveButton(positiveButtonText) { _, _ ->
                positiveButtonClicked.invoke()
            }
            .create()
            .show()
    }

    fun bitMapToString(bitmap: Bitmap): String? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream)
        val b: ByteArray = stream.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun stringToBitmap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: java.lang.Exception) {
            e.message
            null
        }
    }
}