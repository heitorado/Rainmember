package br.ufpe.cin.android.rainmember.dashboard

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
import br.ufpe.cin.android.rainmember.data.WeatherData
import kotlinx.android.synthetic.main.fragment_temperature_comparison.*
import kotlinx.android.synthetic.main.fragment_temperature_comparison.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TempCompFragment : Fragment() {
    companion object {
        const val TAG = "TempCompFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_temperature_comparison, container,false)
        val cityIdPref = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext).getString(getString(R.string.location_comp_preference), getString(R.string.location_comp_preference_default_value))

        val card = view.findViewById<View>(R.id.card_temperature_comparison)
        card.setOnClickListener {
            chooseLocationToCompare()
        }

        if(cityIdPref != null){
            if(cityIdPref == getString(R.string.location_comp_preference_default_value)){
                view.temp_comp_body_text.text = getString(R.string.temperature_comparison_no_city)
            } else {
                view.temp_comp_body_text.text = getString(R.string.temperature_comparison_no_data)
            }
        }


        val db = WeatherDataDB.getDatabase(activity?.applicationContext!!)
        doAsync {
            val weatherData = db.weatherDataDAO().getLatest()
            val weatherDataComparison = db.weatherDataDAO().getLatestComparation()

            if(weatherData != null && weatherDataComparison != null){
                uiThread {
                    it.temp_comp_body_text.text = generateComparisonText(weatherData, weatherDataComparison)
                }
            }


        }

        return view
    }

    private fun generateComparisonText(weatherData: WeatherData, weatherDataComparison: WeatherData): String {
        var resText = ""

        val city1 = weatherData.cityName
        val city2 = weatherDataComparison.cityName
        val currentTemp = weatherData.temperature.toInt()
        val comparingTemp = weatherDataComparison.temperature.toInt()
        val currentHumidity = weatherData.humidity.toInt()
        val comparingHumidity = weatherDataComparison.humidity.toInt()

        // Comparing temperature
        resText += if(currentTemp > comparingTemp) {
            if(currentTemp - comparingTemp >= 10) {
                getString(R.string.temperature_comparison_hotter_2, city1, city2)
            } else {
                getString(R.string.temperature_comparison_hotter_1, city1, city2)
            }
        } else if ( currentTemp < comparingTemp ) {
            if(comparingTemp - currentTemp >= 10) {
                getString(R.string.temperature_comparison_colder_2, city1, city2)
            } else {
                getString(R.string.temperature_comparison_colder_1, city1, city2)
            }
        } else {
            getString(R.string.temperature_comparison_equal_temp, city1, city2)
        }

        resText += "\n\n"

        // Comparing humidity
        resText += if(currentHumidity > comparingHumidity) {
            if(currentHumidity - comparingHumidity >= 40) {
                getString(R.string.temperature_comparison_wet_2, city1, city2)
            } else {
                getString(R.string.temperature_comparison_wet_1, city1, city2)
            }
        } else if ( currentHumidity < comparingHumidity ) {
            if(comparingHumidity - currentHumidity >= 40) {
                getString(R.string.temperature_comparison_dry_2, city1, city2)
            } else {
                getString(R.string.temperature_comparison_dry_1, city1, city2)
            }
        } else {
            getString(R.string.temperature_comparison_equal_humidity, city1, city2)
        }

        resText += "\n\n"

        // Comparing characters
        resText += when {
            city1.length > city2.length -> getString(R.string.temperature_comparison_more_letters, city1, city1.length - city2.length, city2)
            city1.length < city2.length -> getString(R.string.temperature_comparison_less_letters, city1, city2.length - city1.length, city2)
            else -> getString(R.string.temperature_comparison_equal_letters, city1, city2)
        }

        return resText
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
