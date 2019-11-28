package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm
import java.util.*

class AlarmLogic( val context : Context, val alarm : Alarm){
    private var alarmMgr: AlarmManager? = null
    private var alarmIntent: PendingIntent

    init {
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.action = "ALARM_${alarm.alarmTime}"
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
            1000 * 60 * 20,
            alarmIntent
        )
    }

    fun cancelAlarm() {
        alarmMgr?.cancel(alarmIntent)
    }


}