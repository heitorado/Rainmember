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
import org.jetbrains.anko.doAsync

class UmbrellaFragment : Fragment () {

    companion object {
        const val TAG = "UmbrellaFragment"
    }

    private var weatherData: WeatherData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d (TAG, "Created")

        getWeatherData()

        return inflater.inflate(R.layout.fragment_umbrella, container,false)
    }

    fun getWeatherData () {
        doAsync {
            val db = WeatherDataDB.getDatabase(context!!)

            weatherData = db.weatherDataDAO().getLatest()

            Log.d (TAG, weatherData?.temperature.toString())
        }
    }

}