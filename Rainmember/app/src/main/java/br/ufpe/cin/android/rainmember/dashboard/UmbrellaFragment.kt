package br.ufpe.cin.android.rainmember.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import br.ufpe.cin.android.rainmember.data.WeatherData
import kotlinx.android.synthetic.main.fragment_umbrella.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UmbrellaFragment : Fragment () {

    companion object {
        const val TAG = "UmbrellaFragment"
    }

    private val bringUmbrellaConditions = listOf(
        "shower rain",
        "rain",
        "thunderstorm"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d (TAG, "Created")

        val view= inflater.inflate(R.layout.fragment_umbrella, container,false)

        doAsync {
            val db = WeatherDataDB.getDatabase(context!!)

            val weatherData = db.weatherDataDAO().getLatest()

            uiThread {

                val weatherCondition = weatherData.condition
                Log.d (TAG, weatherCondition)
                if (bringUmbrellaConditions.contains(weatherCondition)) {
                    view.action_text.text = "YES"
                    view.action_image.setBackgroundResource(R.drawable.umbrella_yes)
                }

            }
            Log.d (TAG, weatherData?.temperature.toString())
        }


        return view
    }


}