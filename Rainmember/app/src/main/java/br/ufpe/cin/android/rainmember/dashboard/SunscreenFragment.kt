package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import kotlinx.android.synthetic.main.fragment_sunscreen.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SunscreenFragment : Fragment() {

    companion object {
        const val TAG = "SunscreenFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d (TAG, "Created")

        val db = WeatherDataDB.getDatabase(activity?.applicationContext!!)
        doAsync {
            val weatherData = db.weatherDataDAO().getLatest()
            val resourceYes = R.drawable.image_sunscreen_yes
            val resourceNo = R.drawable.image_sunscreen_no
            var hintText = "We couldn't retrieve the UV data right now. But you should wear sunscreen anyway, just in case!"
            var res = resourceYes


            if(weatherData != null) {
                Log.d(TAG, weatherData.toString())

                if (weatherData.currentUv <= 2.9) {
                    hintText = "No need for sunscreen, the sun is being nice at this time!"
                    res = resourceNo
                } else if (weatherData.currentUv > 2.9 && weatherData.currentUv <= 5.9) {
                    hintText = "Remember to put sunscreen! The UV index is Moderate."
                } else if (weatherData?.currentUv > 5.9 && weatherData.currentUv <= 7.9) {
                    hintText =
                        "Remember to put sunscreen and avoid sun exposure between 10am and 4pm! The UV index is High."
                } else if (weatherData.currentUv > 7.9 && weatherData.currentUv <= 10.9) {
                    hintText =
                        "Remember to put A LOT OF sunscreen and avoid sun exposure between 10am and 4pm! The UV index is Very High"
                } else if (weatherData.currentUv > 10.9) {
                    hintText =
                        "Put a LOT of sunscreen every 2 hours. Avoid sun exposure! The UV index is EXTREME. Are you sure you want to leave your house now?"
                }
            }

            uiThread {
                it.sunscreen_hint_text.text = hintText
                it.sunscreen_image.setBackgroundResource(res)
            }
        }

        return inflater.inflate(R.layout.fragment_sunscreen, container,false)
    }
}