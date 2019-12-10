package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import br.ufpe.cin.android.rainmember.MainActivity
import br.ufpe.cin.android.rainmember.R


class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "ALARM TRIGGERED AT: ${intent.extras!!["ALARM_TIME"]}")

        var notification = buildNotification(context, intent)

        with(NotificationManagerCompat.from(context.applicationContext)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, notification.build())
        }
    }

    private fun buildNotification(context : Context, receivedIntent: Intent) : NotificationCompat.Builder {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context.applicationContext, 0, intent, 0)

        return NotificationCompat.Builder(context.applicationContext, context.getString(R.string.channel_id))
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Rainmember, rainmember!")
            .setStyle(NotificationCompat.BigTextStyle()
            .bigText("you asked for weather stuff reminders at ${receivedIntent.extras!!["ALARM_TIME"]}.\n" +
                    "Click here to check it out what today's weather is all about!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}