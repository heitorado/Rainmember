package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import kotlinx.android.synthetic.main.fragment_life_suggestions.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LifeSuggestionFragment : Fragment() {

    companion object {
        const val TAG = "LifeSuggestionFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d (TAG, "Created")

        val db = WeatherDataDB.getDatabase(context!!)
        doAsync {
            val weatherData = db.weatherDataDAO().getLatest()

            if(weatherData != null) {
                val beach =
                    (weatherData.temperature > 28.0 && (weatherData.condition == "clear sky" || weatherData.condition == "few clouds"))

                val netflix =
                    (weatherData.temperature <= 22.0 && weatherData.weatherCode >= 200 && weatherData.weatherCode < 550)

                val bikeOrWalk =
                    (weatherData.temperature > 22.0 && weatherData.weatherCode >= 800)

                val picnic =
                    (weatherData.temperature >= 25.0 && weatherData.weatherCode >= 800)

                val fondue =
                    (weatherData.temperature <= 20.0 && weatherData.weatherCode >= 200 && weatherData.weatherCode <= 761)

                val indoorActivity =
                    (weatherData.temperature <= 22.0 && weatherData.weatherCode >= 800)

                val gameOver =
                    (weatherData.weatherCode == 762 || weatherData.weatherCode == 781)

                var suggestions = ArrayList<String>()

                Log.d(TAG, weatherData.toString())

                val currentTemp = weatherData.temperature.toInt()

                if (beach) suggestions.add(getString(R.string.life_suggestion_beach, currentTemp, weatherData.condition))
                if (netflix) suggestions.add(getString(R.string.life_suggestion_netflix, currentTemp))
                if (bikeOrWalk) suggestions.add(getString(R.string.life_suggestion_bike_or_walking, currentTemp))
                if (picnic) suggestions.add(getString(R.string.life_suggestion_picnic, currentTemp))
                if (fondue) suggestions.add(getString(R.string.life_suggestion_fondue, currentTemp, weatherData.condition))
                if (indoorActivity) suggestions.add(getString(R.string.life_suggestion_indoor, currentTemp))


                uiThread {
                    if (gameOver) {
                        life_tip.text = getString(R.string.life_suggestion_danger, weatherData.condition)
                    } else {
                        if (suggestions.isNotEmpty()) {
                            life_tip.text = suggestions.random()
                        } else {
                            life_tip.text = getString(R.string.life_suggestion_no_suggestion)
                        }
                    }
                }
            } else {
                uiThread {
                    life_tip.text = getString(R.string.life_suggestion_no_data)
                }
            }

        }

        return inflater.inflate(R.layout.fragment_life_suggestions, container,false)
    }
}
