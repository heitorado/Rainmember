package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.alarm.CreateAlarmActivity
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.AlarmDB
import kotlinx.android.synthetic.main.item_alarm.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AlarmAdapter (private val items: ArrayList<Alarm>, private val c: Context): RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    companion object {
        const val TAG = "AlarmAdapter"
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Here we inflate the XML 'item_alarm' that corresponds to one single alarm item. After that we pass the inflated XML to the ViewHolder constructor
        // which will trigger the onBindViewHolder and make all the necessary bindings. The ViewHolder is then returned tho whoever called the adapter, effectively showing what we wanted.
        val view = LayoutInflater.from(c).inflate(R.layout.item_alarm, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val i = items[position]
        holder.alarm_time?.text = i.alarmTime
        holder.alarm_days?.text = i.alarmDays()
        holder.alarm_toggle.isChecked = i.active

        // Here we set the listener for long-pressing the alarm, removing it from the DB.
        holder.itemView.alarm_edit.setOnLongClickListener {
            doAsync {
                val db = AlarmDB.getDatabase(c.applicationContext)

                val alarmToDelete = db.alarmDAO().getAlarm(i.alarmTime)

                if(alarmToDelete != null) {
                    db.alarmDAO().destroyAlarm(alarmToDelete)
                    updateAlarmManager(alarmToDelete)
                }

                uiThread {
                    items.removeAt(position)
                    it.notifyItemRemoved(position)
                    it.notifyItemRangeChanged(position, itemCount)
                }
            }

            true
        }

        // Here we set the alarm as active or inactive, updating in the db.
        holder.itemView.toggle_alarm_on.setOnClickListener {
            // Check for the switch current status
            val switchStatus = holder.alarm_toggle.isChecked

            // create a new object for db storing
            val al = Alarm(switchStatus, i.alarmDates, i.alarmTime)

            doAsync {
                val db = AlarmDB.getDatabase(c.applicationContext)
                db.alarmDAO().addAlarm(al)
                val updatedAlarm = db.alarmDAO().getAlarm(al.alarmTime)

                if(updatedAlarm != null) {
                    // Update the local array so the recyclerView is updated too
                    val ind = items.indexOf( items.find {
                        it.alarmTime == updatedAlarm.alarmTime
                    } )
                    if(ind >= 0) {
                        items[ind].active = switchStatus
                    }

                    updateAlarmManager(updatedAlarm)

                    uiThread {
                        it.notifyItemChanged(ind)
                    }
                }
            }
        }
    }

    private fun updateAlarmManager(alarm : Alarm) {
        // Set or cancel the AlarmManager for this alarm
        var alarmConfig = AlarmLogic(context = c.applicationContext, alarm = alarm)
        val alarmDays = alarm.weekDaysArray()
        if (alarm.active) {
            for( i in 0 until alarmDays.size){
                alarmConfig.setAlarm(alarmDays[i])
            }
        }
        else {
            for( i in 0 until alarmDays.size){
                alarmConfig.cancelAlarm(alarmDays[i])
            }
        }
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val alarm_time = item.alarm_time_text
        val alarm_days = item.alarm_days_text
        val alarm_toggle = item.toggle_alarm_on
    }
}