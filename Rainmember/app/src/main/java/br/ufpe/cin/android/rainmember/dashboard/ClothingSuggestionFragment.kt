package br.ufpe.cin.android.rainmember.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import br.ufpe.cin.android.rainmember.data.WeatherData
import kotlinx.android.synthetic.main.fragment_clothing_suggestion.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
                val suggestion = getClothesSuggestion(weatherData)

                uiThread {
                    top_clothing.setBackgroundResource(suggestion.top)
                    bottom_clothing.setBackgroundResource(suggestion.bottom)
                    suggestion_message.text = getString(suggestion.message)
                }
            }
        }
    }

    inner class ClothesSuggestion (val top: Int, val bottom: Int, val message: Int)

    private fun getClothesSuggestion (weatherData: WeatherData): ClothesSuggestion {

        return when {
            weatherData.minTemperature < 18 ->
                ClothesSuggestion(R.drawable.sweater, R.drawable.trousers, R.string.clothe_suggestion_cold)
            weatherData.maxTemperature > 28 ->
                ClothesSuggestion(R.drawable.shirt, R.drawable.shorts, R.string.clothe_suggestion_hot)
            else ->
                ClothesSuggestion(R.drawable.shirt, R.drawable.trousers, R.string.clothe_suggestion_nice)
        }
    }

}