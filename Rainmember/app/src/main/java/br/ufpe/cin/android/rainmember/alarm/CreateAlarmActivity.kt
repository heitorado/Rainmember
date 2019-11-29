package br.ufpe.cin.android.rainmember.alarm

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm.AlarmLogic
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.AlarmDB

import kotlinx.android.synthetic.main.activity_create_alarm.*
import kotlinx.android.synthetic.main.content_create_alarm.*
import kotlinx.android.synthetic.main.content_create_alarm.view.*
import org.jetbrains.anko.doAsync

class CreateAlarmActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CreateAlarmActivity"
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_alarm)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var resultText : String

            if(validAlarmConfig(view.rootView)){
                saveAlarmSettings(view.rootView)
                resultText = "Alarm created successfully!"
                Log.d(TAG, "Alarm created")
            } else {
                resultText = "Error! Select at least one day of the week!"
                Log.d(TAG, "Error creating alarm")
            }

            Snackbar.make(view, resultText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        alarm_time_picker.setIs24HourView(true)

        alarm_time_picker.setOnTimeChangedListener { _: TimePicker, i: Int, i1: Int ->
            Log.d(TAG, "Time selector changed")
            Log.d(TAG, i.toString())
            Log.d(TAG, i1.toString())
            // Experimental
            //saveAlarmSettings(timePicker.rootView)
        }

        checkBox_monday.setOnClickListener {
            Log.d(TAG, "Monday checkbox clicked")
            // Experimental
            // saveAlarmSettings(it.rootView)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun saveAlarmSettings(v : View)  {
        val h = v.alarm_time_picker.hour
        val m = v.alarm_time_picker.minute
        val days = getCheckedDaysValue(v)

        val al = Alarm(true, days,"$h:$m")

        doAsync {
            val db = AlarmDB.getDatabase(applicationContext)
            db.alarmDAO().addAlarm(al)
            val createdAlarm = db.alarmDAO().getAlarm(al.alarmTime)

            if(createdAlarm != null){
                var alarmConfig = AlarmLogic(context = applicationContext, alarm = createdAlarm)
                alarmConfig.setAlarm()
            }
        }

    }

    private fun getCheckedDaysValue(v : View) : Int{
        var daysValue = 0

        // Monday
        if (v.checkBox_monday.isChecked) daysValue += 1

        // Tuesday
        if (v.checkBox_tuesday.isChecked) daysValue += 2

        // Wednesday
        if (v.checkBox_wednesday.isChecked) daysValue += 4

        // Thursday
        if (v.checkBox_thursday.isChecked) daysValue += 8

        // Friday
        if (v.checkBox_friday.isChecked) daysValue += 16

        // Saturday
        if (v.checkBox_saturday.isChecked) daysValue += 32

        // Sunday
        if (v.checkBox_sunday.isChecked) daysValue += 64

        return daysValue
    }

    private fun validAlarmConfig(v : View) : Boolean {
        return v.checkBox_monday.isChecked || v.checkBox_tuesday.isChecked ||
               v.checkBox_wednesday.isChecked || v.checkBox_thursday.isChecked ||
               v.checkBox_friday.isChecked || v.checkBox_saturday.isChecked ||
               v.checkBox_sunday.isChecked
    }
}
