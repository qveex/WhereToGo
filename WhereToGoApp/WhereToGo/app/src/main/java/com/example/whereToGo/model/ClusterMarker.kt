package com.example.whereToGo.model

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class ClusterMarker (

    private val position: LatLng,
    private val title: String,
    private val snippet: String,
    val image: Bitmap,
    private val place: Place,

) : ClusterItem {

    override fun getPosition() = position

    override fun getTitle() = title

    override fun getSnippet() = snippet

}