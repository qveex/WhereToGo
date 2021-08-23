package com.example.whereToGo.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whereToGo.R
import com.example.whereToGo.viewmodel.PlaceViewModelDb
import kotlinx.android.synthetic.main.fragment_places.view.*


class PlacesFragment : Fragment() {

    private val adapter by lazy { PlacesAdapter() }
    private lateinit var placeViewModelDb: PlaceViewModelDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_places, container, false)

        placeViewModelDb = ViewModelProvider(this).get(PlaceViewModelDb::class.java)

        view.places_list.adapter = adapter
        view.places_list.layoutManager = LinearLayoutManager(requireContext())

        placeViewModelDb.getAllData.observe(requireActivity(), {
            adapter.setData(it)
        })

        return view
    }

}