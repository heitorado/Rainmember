package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.alarm.CreateAlarmActivity
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm
import kotlinx.android.synthetic.main.fragment_alarms.view.*
import java.util.ArrayList

class AlarmsFragment : Fragment() {

    companion object {
        const val TAG = "AlarmsFragment"
        var alarmList = ArrayList<Alarm>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_alarms, container, false)

        // Floating button that starts the activity for alarm creation
        view.add_alarm.setOnClickListener {
            Log.d(TAG, "floating button clicked!")

            val ctx = this.context!!.applicationContext
            val createAlarmIntent = Intent(ctx, CreateAlarmActivity::class.java)

            // Not really sure why this flag is needed, but it makes the app work as intended.
            createAlarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            ctx.startActivity(createAlarmIntent)
        }

        // Set up recyclerView
        view.alarmRecyclerView.layoutManager = LinearLayoutManager(this.context)
        view.alarmRecyclerView.adapter = AlarmAdapter(alarmList, this.context!!.applicationContext)
        view.alarmRecyclerView.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))

        Log.d(TAG, "Created")

        return view
    }


}
