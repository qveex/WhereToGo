package com.example.whereToGo.fragments.list

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whereToGo.R
import com.example.whereToGo.adapter.PlacesAdapter
import com.example.whereToGo.repository.ServerPlaceRepository
import com.example.whereToGo.viewmodel.PlaceViewModelDb
import com.example.whereToGo.viewmodel.ServerPlaceViewModelFactory
import com.example.whereToGo.viewmodel.PlaceViewModelServer
import kotlinx.android.synthetic.main.fragment_places.view.*
import java.lang.Exception
import java.net.SocketTimeoutException


class PlacesFragment : Fragment() {

    private val adapter by lazy { PlacesAdapter() }
    private lateinit var placeViewModelDb: PlaceViewModelDb
    private lateinit var placeViewModelServer: PlaceViewModelServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_places, container, false)

        val viewModelFactory = ServerPlaceViewModelFactory(ServerPlaceRepository())
        placeViewModelServer = ViewModelProvider(this, viewModelFactory).get(PlaceViewModelServer::class.java)
        placeViewModelDb = ViewModelProvider(this).get(PlaceViewModelDb::class.java)

        view.places_list.adapter = adapter
        view.places_list.layoutManager = LinearLayoutManager(requireContext())

        /*if (isOnline(requireContext())) {

            placeViewModelServer.getCity("Saint Petersburg")
            placeViewModelServer.listResponse.observe(requireActivity(), Observer { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.setData(it)
                        placeViewModelDb.addPlaces(it)
                    }
                } else {
                    Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_LONG).show()
                }
            })
        } else {*/
            Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_LONG).show()
            placeViewModelDb.getAllData.observe(requireActivity(), {
                adapter.setData(it)
            })
        //}

        return view
    }


    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
             when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}