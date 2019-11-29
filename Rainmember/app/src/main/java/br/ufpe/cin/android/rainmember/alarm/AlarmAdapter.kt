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

class AlarmAdapter (private val items: List<Alarm>, private val c: Context): RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

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
                    if(ind > 0) {
                        items[ind].active = switchStatus
                    }

                    // Set or cancel the AlarmManager for this alarm
                    var alarmConfig = AlarmLogic(context = it.context, alarm = updatedAlarm)
                    if (updatedAlarm.active) {
                        Log.d(TAG, "Requesting ALARM SET for ${updatedAlarm.alarmTime}")
                        alarmConfig.setAlarm()
                    }
                    else {
                        Log.d(TAG, "Requesting ALARM CANCEL for ${updatedAlarm.alarmTime}")
                        alarmConfig.cancelAlarm()

                    }
                }

                //FIXME here we have a bug: the recyclerview keeps fetching for the static list and not for the db, so it needs refreshing for updating all the sliders.
            }
        }

        holder.itemView.alarm_edit.setOnClickListener {

            Log.d("AlarmAdapter", "Clicou no alarme")

            //val createAlarmIntent = Intent(c, CreateAlarmActivity::class.java)

            // Not really sure why this flag is needed, but it makes the app work as intended.
            //createAlarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Start the new activity with the provided intent and layout
            //c.startActivity(createAlarmIntent)
        }
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val alarm_time = item.alarm_time_text
        val alarm_days = item.alarm_days_text
        val alarm_toggle = item.toggle_alarm_on
    }
}