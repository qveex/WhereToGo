package com.example.whereToGo.fragments.place

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whereToGo.R
import com.example.whereToGo.utilities.Converters
import kotlinx.android.synthetic.main.fragment_place.view.*

class PlaceFragment : Fragment() {

    private val args by navArgs<PlaceFragmentArgs>()
    //private lateinit var placeViewModel: PlaceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_place, container, false)
        val converter = Converters()
        view.item_image.setImageBitmap(converter.toBitmap(args.currentPlace.image.toByteArray()))
        view.item_name.text = args.currentPlace.name
        view.item_counter.text = getString(R.string.place_counter_i, args.currentPlace.visitCounter.toString())
        //view.item_address.text = args.currentPlace.name
        view.item_description.text = args.currentPlace.description

        //placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        view.item_go_btn.setOnClickListener {
            val action = PlaceFragmentDirections.actionPlaceFragmentToMapsFragment().setReqPlace(args.currentPlace)
            view.findNavController().navigate(action)
        }

        return view
    }
}