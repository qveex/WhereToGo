package com.example.whereToGo.repository

import com.example.whereToGo.api.RetrofitInstance
import com.example.whereToGo.model.Place

class ServerPlaceRepository {

    suspend fun getPlaces(): List<Place> {
        return RetrofitInstance.api.getPlaces()
    }
}