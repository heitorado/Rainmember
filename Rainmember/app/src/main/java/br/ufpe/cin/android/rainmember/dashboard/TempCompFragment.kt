package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import br.ufpe.cin.android.rainmember.dashboard.ChooseComparingLocationActivity
import kotlinx.android.synthetic.main.fragment_temperature_comparison.view.*
import org.jetbrains.anko.doAsync

class TempCompFragment : Fragment() {
    companion object {
        const val TAG = "TempCompFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d (TAG, "Created")

        val db = WeatherDataDB.getDatabase(context!!)
        doAsync {
            val weatherData = db.weatherDataDAO().getLatest()
            if(weatherData != null){
                Log.d (TAG, weatherData.temperature.toString())
            }
        }

        val view = inflater.inflate(R.layout.fragment_temperature_comparison, container,false)

        var card = view.findViewById<View>(R.id.card_temperature_comparison)
        card.setOnClickListener {
            Log.d(TAG, "Temperature Comparison Card Clicked")
            chooseLocationToCompare()
        }

        return view
    }

    private fun chooseLocationToCompare() {
        //val newFragment = AvailableLocationsDialogFragment()
        //newFragment.show(this.fragmentManager!!, "available_locations")

        val chooseLocationIntent = Intent(context, ChooseComparingLocationActivity::class.java)

        // Not really sure why this flag is needed, but it makes the app work as intended.
        chooseLocationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        context!!.startActivity(chooseLocationIntent)
    }
}
