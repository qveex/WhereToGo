package com.example.whereToGo.utilities

import androidx.recyclerview.widget.DiffUtil
import com.example.whereToGo.model.Place

class PlaceDiffUtill(
    private val oldList: List<Place>,
    private val newList: List<Place>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = when {
        oldList[oldItemPosition].id != newList[newItemPosition].id -> false
        oldList[oldItemPosition].name != newList[newItemPosition].name -> false
        oldList[oldItemPosition].latitude != newList[newItemPosition].latitude -> false
        oldList[oldItemPosition].longitude != newList[newItemPosition].longitude -> false
        oldList[oldItemPosition].description != newList[newItemPosition].description -> false
        else -> true
    }
}