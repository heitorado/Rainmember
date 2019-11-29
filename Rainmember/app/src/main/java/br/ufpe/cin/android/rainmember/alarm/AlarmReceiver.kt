package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast



class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context.applicationContext, "Alarm Manager just ran", Toast.LENGTH_LONG).show()
        Log.d(TAG, "ALARM TRIGGERED AT: ${intent.extras!!["ALARM_TIME"]}")
    }
}