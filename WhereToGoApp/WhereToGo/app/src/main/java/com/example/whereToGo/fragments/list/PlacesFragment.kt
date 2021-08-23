package com.example.whereToGo.fragments.list

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whereToGo.R
import com.example.whereToGo.model.Place
import com.example.whereToGo.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.fragment_places.view.*


class PlacesFragment : Fragment() {

    private val adapter by lazy { PlacesAdapter() }
    private lateinit var placeViewModel: PlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_places, container, false)

        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        view.places_list.adapter = adapter
        view.places_list.layoutManager = LinearLayoutManager(requireContext())

        placeViewModel.getAllData.observe(requireActivity(), {
            adapter.setData(it)
        })

        return view
    }

}