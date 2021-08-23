package com.example.whereToGo.api

import com.example.whereToGo.utilities.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: PlaceApi by lazy {
        retrofit.create(PlaceApi::class.java)
    }

}