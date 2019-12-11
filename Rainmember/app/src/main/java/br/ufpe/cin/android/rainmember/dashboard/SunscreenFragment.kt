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
        val db = WeatherDataDB.getDatabase(activity?.applicationContext!!)
        doAsync {
            val weatherData = db.weatherDataDAO().getLatest()
            val resourceYes = R.drawable.image_sunscreen_yes
            val resourceNo = R.drawable.image_sunscreen_no
            var hintText = getString(R.string.sunscreen_no_data)
            var res = resourceYes


            if(weatherData != null) {
                if (weatherData.currentUv <= 2.9) {
                    hintText = getString(R.string.sunscreen_dont_need)
                    res = resourceNo
                } else if (weatherData.currentUv > 2.9 && weatherData.currentUv <= 5.9) {
                    hintText = getString(R.string.sunscreen_moderate)
                } else if (weatherData?.currentUv > 5.9 && weatherData.currentUv <= 7.9) {
                    hintText = getString(R.string.sunscreen_high)
                } else if (weatherData.currentUv > 7.9 && weatherData.currentUv <= 10.9) {
                    hintText = getString(R.string.sunscreen_very_high)
                } else if (weatherData.currentUv > 10.9) {
                    hintText = getString(R.string.sunscreen_extreme)
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