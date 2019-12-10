package br.ufpe.cin.android.rainmember.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard.LocationAdapter
import kotlinx.android.synthetic.main.activity_choose_comparing_location.*
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class ChooseComparingLocationActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CompLocActivity"
        val availableLocationsList = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_comparing_location)

        doAsync {
            if(availableLocationsList.isEmpty()) {
                val countryCodesFile = File(applicationContext.filesDir.absolutePath, "countryCodes.json")

                if(countryCodesFile.exists()) {
                    var countriesJSON = JSONArray(countryCodesFile.readText())

                    for(i in 0 until countriesJSON.length()){
                        availableLocationsList.add( getLocationAsText(countriesJSON.getJSONObject(i)))
                    }

                    availableLocationsList.sort()
                }
            }
        }

        // Set up recyclerView
        locationRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        locationRecyclerView.adapter = LocationAdapter(availableLocationsList, applicationContext, this)
        locationRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))

    }

    private fun getLocationAsText(jsonObject: JSONObject): String {
        return "${jsonObject.getString("name")}|${jsonObject.getString("country")}|${jsonObject.getString("id")}"
    }
}
