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

                if (beach) suggestions.add("With the current temperature of ${weatherData.temperature}°C and ${weatherData.condition}, It’s a great day for going to the beach! Don’t forget your sunscreen!")
                if (netflix) suggestions.add("It's ${weatherData.temperature}°C outside and raining. What about making some microwave popcorn (I heard microwaves have a button just for that) and watch something on your favorite streaming platform?")
                if (bikeOrWalk) suggestions.add("It's not raining and about ${weatherData.temperature}°C outside, temperature which the writer of this recommendation finds pretty comfortable for biking or walking on the park. If you think that too, take that as a suggestion!")
                if (picnic) suggestions.add("Well, it's ${weatherData.temperature}°C outside and the weather is clear. Why don't you get that checkered towel, a straw basket, fruits, wine, that nice company and go on a picnic?")
                if (fondue) suggestions.add("${weatherData.temperature}°C and precipitation means just one thing: fondue. Okay, maybe it could mean a thousand other things, but you got it. Just google 'fondue' and give it a try!")
                if (indoorActivity) suggestions.add("Maybe ${weatherData.temperature}°C is not the best weather for outdoor stuff, but going on indoor activities can be really nice. What about going to a nice restaurant, shopping or even indoor climbing?")


                uiThread {
                    if (gameOver) {
                        life_tip.text = "Okay, so the forecast told us there is a ${weatherData.condition} at your location. Maybe worrying about fancy activities might not be the best thing to do at the moment. We hope you are okay."
                    } else {
                        if (suggestions.isNotEmpty()) {
                            life_tip.text = suggestions.random()
                        } else {
                            life_tip.text =
                                "We don't have any recommendations for now. But know that can do anything you want! Go ahead, the world is yours!"
                        }
                    }
                }
            } else {
                uiThread {
                    life_tip.text = "We don't have any idea of the weather conditions at your location, so we can't recommend anything. That's okay, It's not like you depend on us for living or something like that. Just do what you wanna do."
                }
            }

        }

        return inflater.inflate(R.layout.fragment_life_suggestions, container,false)
    }
}
