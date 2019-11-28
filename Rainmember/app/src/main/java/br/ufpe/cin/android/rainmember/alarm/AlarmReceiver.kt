package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast



class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context.applicationContext, "Alarm Manager just ran", Toast.LENGTH_LONG).show()
    }
}