package com.example.whereToGo.fragments.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whereToGo.R
import com.example.whereToGo.model.ClusterMarker
import com.example.whereToGo.utilities.ClusterManagerRenderer
import com.example.whereToGo.utilities.Converters
import com.example.whereToGo.viewmodel.PlaceViewModelDb
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.GeoApiContext
import com.google.maps.android.clustering.ClusterManager
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

// Сохранение состояния карты
// https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial?hl=ru


class MapsFragment : Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val mGeoApiContext by lazy { GeoApiContext.Builder().apiKey(getString(R.string.google_maps_API_key)).build() }
    private val mClusterManager by lazy { ClusterManager<ClusterMarker>(requireContext(), mMap) }
    private val mClusterManagerRenderer by lazy { ClusterManagerRenderer(requireContext(), mMap, mClusterManager) }

    private lateinit var placeViewModelDb: PlaceViewModelDb

    private val args by navArgs<MapsFragmentArgs>()
    private val DEFAULT_ZOOM = 15f


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        placeViewModelDb = ViewModelProvider(this).get(PlaceViewModelDb::class.java)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return view
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mapInit()
        addMapMarkers()
    }


    private fun addMapMarkers() {

        mClusterManager.renderer = mClusterManagerRenderer

        placeViewModelDb.getAllData.observe(requireActivity(), {

            for (place in it) {

                val converter = Converters()
                val snippet = place.visitCounter.toString()
                val img = place.image
                val location = LatLng(place.latitude, place.longitude)
                val title = place.name

                val newClusterMarker = ClusterMarker(location, title, snippet, converter.toBitmap(img.toByteArray()), place)
                mClusterManager.addItem(newClusterMarker)

                // add mark to list of ALL THE MARKERS (to remove or smth other)
            }
            mClusterManager.cluster()

            mMap.setOnInfoWindowClickListener { m ->
                val action = MapsFragmentDirections.actionMapsFragmentToPlaceFragment(it.first())
                findNavController().navigate(action)
            }

        })
    }


    /*@SuppressLint("MissingPermission")
    private fun calculateDirections(place: Place) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {

            addMark(LatLng(place.latitude, place.longitude), place.name)

            val destination = com.google.maps.model.LatLng(place.latitude, place.longitude)
            val directions = DirectionsApiRequest(mGeoApiContext)

            directions.alternatives(true)
            directions.origin(com.google.maps.model.LatLng(it.latitude, it.longitude))

            directions.destination(destination).setCallback(object : PendingResult.Callback<DirectionsResult> {

                override fun onResult(result: DirectionsResult) {
                    Log.i("location", "onResult: routes: " + result.routes[0].toString())
                    Log.i("location", "onResult: geocodedWayPoints: " + result.geocodedWaypoints[0].toString())

                    //addPolylinesToMap(result)
                }

                override fun onFailure(e: Throwable) {
                    Log.e("location", "onFailure: " + e.message)
                }
            })

        }
    }


    private fun addPolylinesToMap(result: DirectionsResult) {
        Handler(Looper.getMainLooper()).post(Runnable {
            for (route in result.routes) {
                val decodedPath = PolylineEncoding.decode(route.overviewPolyline.encodedPath)
                val newDecodedPath: MutableList<LatLng> = ArrayList()

                // This loops through all the LatLng coordinates of ONE polyline.
                for (latLng in decodedPath)
                    newDecodedPath.add(LatLng(latLng.lat, latLng.lng))

                val polyline: Polyline = mMap.addPolyline(PolylineOptions().addAll(newDecodedPath))
                polyline.color = ContextCompat.getColor(requireContext(), R.color.lighter_dark_grey)
                polyline.isClickable = true
            }
        })
    }*/




    private fun addMark(coordinates: LatLng, title: String){
        mMap.addMarker(
            MarkerOptions()
                .position(coordinates)
                .title(title))
    }
    private fun moveCamera(coordinates: LatLng, zoom: Float) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom))
    }



    @SuppressLint("MissingPermission")
    private fun mapInit() {

        // map settings
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_dark_theme))
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }

        if (hasLocationPermissions()) {

            mMap.isMyLocationEnabled = true
            // get current location of device
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {

                //val geoCoder = Geocoder(requireContext())
                //val currentLocation = geoCoder.getFromLocation(it.latitude, it.longitude,1)

                if (args.reqPlace != null) moveCamera(LatLng(args.reqPlace!!.latitude, args.reqPlace!!.longitude), DEFAULT_ZOOM)
                else moveCamera(LatLng(it.latitude, it.longitude), DEFAULT_ZOOM)

                //addMark(LatLng(it.latitude, it.longitude), currentLocation.first().locality)
            }

        } else {
            requestLocationPermission()
        }


    }










    private fun hasLocationPermissions() = EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)


    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms.first())) {
            SettingsDialog.Builder(requireContext()).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(requireContext(), "Permission Granted!", Toast.LENGTH_SHORT).show()
        mapInit()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun requestLocationPermission() {

        EasyPermissions.requestPermissions(
            this,
            "This application can't work without Location Permission",
            1234,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

}