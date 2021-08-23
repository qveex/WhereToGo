package com.example.whereToGo.fragments.add

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.whereToGo.R
import com.example.whereToGo.model.Place
import com.example.whereToGo.viewmodel.PlaceViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment(), OnMapReadyCallback {

    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var positionMarker: Marker? = null
    private var img: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.item_get_placeLocation) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
            view.item_get_image_btn.setImageURI(it)
            view.item_get_image_btn.setBackgroundColor(resources.getColor(R.color.dark_grey))
            img = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
        }

        // https://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude
        view.item_get_image_btn.setOnClickListener { getContent.launch("image/*") }
        view.add_new_place_btn.setOnClickListener { insertData() }

        return view
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initMap()
        positionMarker?.position = mMap.cameraPosition.target
    }



    @SuppressLint("MissingPermission")
    private fun initMap() {

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_dark_theme))
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {

            val location = LatLng(it.latitude, it.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }


        positionMarker = mMap.addMarker(MarkerOptions()
            .position(mMap.cameraPosition.target)
            .title(item_get_name.text.toString())
            .draggable(false))

        mMap.setOnCameraMoveListener {
            positionMarker?.position = mMap.cameraPosition.target
        }
    }

    private fun insertData() {

        if (img != null) {

            // add to db
            val newPlace = Place(
                1337,
                item_get_name.text.toString(),
                item_get_description.text.toString(),
                0,
                img!!,
                positionMarker!!.position.latitude,
                positionMarker!!.position.longitude
            )
            placeViewModel.addPlace(newPlace)

            // add to server



            Toast.makeText(requireContext(), "Место добавлено успешно!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_mapsFragment)

        } else {
            Toast.makeText(requireContext(), "Попробуйте выбрать изображение еще раз", Toast.LENGTH_LONG).show()
        }

    }
}