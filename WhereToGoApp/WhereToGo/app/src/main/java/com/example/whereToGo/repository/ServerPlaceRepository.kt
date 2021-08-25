package com.example.whereToGo.repository

import com.example.whereToGo.api.RetrofitInstance
import com.example.whereToGo.model.Place
import retrofit2.Response

class ServerPlaceRepository {

    suspend fun getPlaces(): Response<List<Place>> {
        return RetrofitInstance.api.getPlaces()
    }

    suspend fun getPlace(id: Int): Response<Place> {
        return RetrofitInstance.api.getPlace(id)
    }

    suspend fun getCity(city: String): Response<List<Place>> {
        return RetrofitInstance.api.getCity(city)
    }
}