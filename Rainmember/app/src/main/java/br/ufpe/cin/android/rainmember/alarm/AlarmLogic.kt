package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm
import java.util.*

class AlarmLogic( val context : Context, val alarm : Alarm){
    companion object {
        const val TAG = "AlarmLogic"
    }

    private var alarmMgr: AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun generateIntent() : PendingIntent {
        return Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.action = "ALARM_TRIGGERED"
            intent.data = Uri.parse("custom://alarm_${alarm.alarmTime}")
            intent.putExtra("ALARM_TIME", alarm.alarmTime)
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
    }


    fun setAlarm() {
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, alarm.alarmTime.split(':').first().toInt())
            set(Calendar.MINUTE,  alarm.alarmTime.split(':').last().toInt())
        }

        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * 1440,
            generateIntent()
        )

        Log.d(TAG, "AlarmManager SET for ${alarm.alarmTime}")
    }

    fun cancelAlarm() {
        alarmMgr?.cancel(generateIntent())
        Log.d(TAG, "AlarmManager CANCELLED for ${alarm.alarmTime}")

    }
}