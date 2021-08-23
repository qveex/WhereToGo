package com.example.whereToGo.utilities

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import com.example.whereToGo.R
import com.example.whereToGo.model.ClusterMarker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator


class ClusterManagerRenderer(

    private val context: Context,
    private val map: GoogleMap,
    private val clusterManager: ClusterManager<ClusterMarker>

) : DefaultClusterRenderer<ClusterMarker>(context, map, clusterManager) {

    private var iconGenerator: IconGenerator = IconGenerator(context.applicationContext)
    private var imageView: ImageView = ImageView(context.applicationContext)
    private var markerWidth = context.resources.getDimension(R.dimen.custom_marker_image).toInt()
    private var markerHeight = context.resources.getDimension(R.dimen.custom_marker_image).toInt()

    init {

        imageView.layoutParams = ViewGroup.LayoutParams(markerWidth, markerHeight)
        val padding = context.resources.getDimension(R.dimen.custom_marker_padding).toInt()
        imageView.setPadding(padding, padding, padding, padding)
        iconGenerator.setContentView(imageView)
    }

    override fun onBeforeClusterItemRendered(item: ClusterMarker?, markerOptions: MarkerOptions?) {
        imageView.setImageBitmap(item?.image)
        imageView.setBackgroundColor(getColor(R.color.lighter_dark_grey))
        val icon = iconGenerator.makeIcon()
        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon))?.title(item?.title)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarker>?) = false

    override fun getColor(clusterSize: Int) = Color.rgb(89, 87, 87)

}

