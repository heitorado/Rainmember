package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.rainmember.R
import kotlinx.android.synthetic.main.item_location.view.*
import org.jetbrains.anko.doAsync

class LocationAdapter(private val items: List<String>, private val c: Context, private val actv : Activity): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    companion object {
        const val TAG = "LocationAdapter"
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Here we inflate the XML 'item_alarm' that corresponds to one single alarm item. After that we pass the inflated XML to the ViewHolder constructor
        // which will trigger the onBindViewHolder and make all the necessary bindings. The ViewHolder is then returned tho whoever called the adapter, effectively showing what we wanted.
        val view = LayoutInflater.from(c).inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val i = items[position]
        holder.country_info?.text = "${i.split('|').first()} - ${i.split('|')[1]}"

        holder.itemView.location_text_container.setOnClickListener {
            var sharedPref = PreferenceManager.getDefaultSharedPreferences(this.c.applicationContext)
            sharedPref.edit {
                this.putString(c.getString(R.string.location_comp_preference), i.split("|").last())
                this.commit()
            }

            // Send broadcast to update dashboard
            val intent = Intent(c.applicationContext.getString(R.string.dashboard_change))
            LocalBroadcastManager.getInstance(c.applicationContext).sendBroadcast(intent)

            actv.finish()
        }
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val country_info = item.location_info_text
    }
}