package com.example.whereToGo.api

import com.example.whereToGo.model.Place
import retrofit2.http.GET

interface PlaceApi {

    @GET("/place")
    suspend fun getPlaces(): List<Place>
}