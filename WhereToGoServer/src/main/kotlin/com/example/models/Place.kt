package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: Int? = null,
    val name: String,
    val description: String,
    val visitCounter: Int,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val city: String
    )
