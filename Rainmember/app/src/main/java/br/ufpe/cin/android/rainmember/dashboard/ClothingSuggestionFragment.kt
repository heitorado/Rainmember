package br.ufpe.cin.android.rainmember.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import br.ufpe.cin.android.rainmember.data.WeatherData
import org.jetbrains.anko.doAsync

class ClothingSuggestionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_clothing_suggestion, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = WeatherDataDB.getDatabase(context!!)

        doAsync {
            val weatherData = db.weatherDataDAO().getLatest()

            if (weatherData != null) {
                val clothes = getClothesSuggestion(weatherData)
                
            }
        }
    }

    private fun getClothesSuggestion (weatherData: WeatherData): Array<String> {
        return when {
            weatherData.minTemperature < 18 -> arrayOf("sweater", "trousers")
            weatherData.maxTemperature > 28 -> arrayOf("t-shirt", "shorts")
            else -> arrayOf("t-shirt", "trousers")
        }
    }

}