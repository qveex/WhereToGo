package com.example.whereToGo.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.whereToGo.utilities.PlaceDiffUtill
import com.example.whereToGo.R
import com.example.whereToGo.model.Place
import kotlinx.android.synthetic.main.place_item_row.view.*

class PlacesAdapter: RecyclerView.Adapter<PlacesAdapter.MyViewHolder>() {

    private var placeList = emptyList<Place>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.place_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = placeList[position]

        holder.itemView.place_name.text = currentItem.name
        holder.itemView.counter_text.text = "Уже посетили: ${currentItem.visitCounter}" // getString not works
        holder.itemView.place_counter.isVisible = false
        //holder.itemView.place_counter.text = currentItem.visitCounter.toString()
        //holder.itemView.place_image.load(currentItem.placeImage)
        holder.itemView.place_image.setImageBitmap(currentItem.placeImage)

        holder.itemView.place_layout.setOnClickListener {

            val action = PlacesFragmentDirections.actionPlacesFragmentToPlaceFragment(currentItem)
            holder.itemView.findNavController().navigate(action)

        }
    }

    override fun getItemCount() = placeList.size

    fun setData(newPlaceList: List<Place>) {
        val diffUtill = PlaceDiffUtill(placeList, newPlaceList)
        val diffResults = DiffUtil.calculateDiff(diffUtill)
        placeList = newPlaceList
        diffResults.dispatchUpdatesTo(this)
    }
}