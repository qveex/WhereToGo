package com.example.whereToGo.repository

import androidx.lifecycle.LiveData
import com.example.whereToGo.data.PlaceDao
import com.example.whereToGo.model.Place

class PlaceRepository(private var placeDao: PlaceDao) {

    val getAllData: LiveData<List<Place>> = placeDao.getAllPlaces()

    suspend fun addPlace(place: Place) {
        placeDao.addPlace(place)
    }

    suspend fun updatePlace(place: Place) {
        placeDao.updatePlace(place)
    }

    suspend fun deletePlace(place: Place) {
        placeDao.deletePlace(place)
    }

    suspend fun deleteAllPlaces() {
        placeDao.deleteAllPlaces()
    }
}