package com.example.whereToGo.api

import com.example.whereToGo.model.Place
import retrofit2.Response
import retrofit2.http.*

interface PlaceApi {

    @GET("places")
    suspend fun getPlaces(): Response<List<Place>>

    @GET("places/{id}")
    suspend fun getPlace(@Path("id") id: Int): Response<Place>

    @GET("places")
    suspend fun getCity(@Query("city") city: String): Response<List<Place>>

    @POST("places")
    suspend fun createPlace(@Body place: Place): Response<Place>
}