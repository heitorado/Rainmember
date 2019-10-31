package br.ufpe.cin.android.rainmember


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DashboardFragment : Fragment() {

    companion object {
        const val TAG = "DashboardFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Created")

        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

}
