package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import org.jetbrains.anko.doAsync

class SunscreenFragment : Fragment() {

    companion object {
        const val TAG = "SunscreenFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d (TAG, "Created")

        val db = WeatherDataDB.getDatabase(context!!)
        doAsync {
            val weatherData = db.weatherDataDAO().getLatest()

            Log.d (TAG, weatherData.temperature.toString())
        }

        return inflater.inflate(R.layout.fragment_sunscreen, container,false)
    }
}