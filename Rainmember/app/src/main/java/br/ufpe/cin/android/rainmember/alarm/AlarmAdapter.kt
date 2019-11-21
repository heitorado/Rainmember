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
import kotlinx.android.synthetic.main.item_alarm.view.*

class AlarmAdapter (private val items: List<Alarm>, private val c: Context): RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Here we inflate the XML 'item_alarm' that corresponds to one single alarm item. After that we pass the inflated XML to the ViewHolder constructor
        // which will trigger the onBindViewHolder and make all the necessary bindings. The ViewHolder is then returned tho whoever called the adapter, effectively showing what we wanted.
        val view = LayoutInflater.from(c).inflate(R.layout.item_alarm, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val i = items[position]
        holder.alarm_time?.text = i.alarmTime.toString()
        holder.alarm_days?.text = i.alarmDates.toString()

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
    }
}