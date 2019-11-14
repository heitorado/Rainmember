package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ufpe.cin.android.rainmember.R

class AlarmsFragment : Fragment() {

    companion object {
        const val TAG = "AlarmsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Created")

        return inflater.inflate(R.layout.fragment_alarms, container, false)
    }


}
