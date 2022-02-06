package com.example.whereToGo.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "place_table")
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val visitCounter: Int,
    val image: Bitmap,

    val latitude: Double,
    val longitude: Double,
    val city: String
): Parcelable