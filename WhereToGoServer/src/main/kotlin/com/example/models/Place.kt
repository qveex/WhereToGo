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

val placeStorage = mutableListOf<Place>(
    Place(1,"Парк", "Небольшой зеленый парк со скамейками", 12, "img1", 59.893228, 30.417227, "Saint Petersburg"),
    Place(2,"Исаакиевский собор", "Большой и красивый собор с парком и площадью рядом", 999, "img2", 59.933792, 30.306833, "Saint Petersburg"),
    Place(3,"Сенная площадь", "Большая площадь с красивым видом, магазинами и кафе", 501, "img3", 59.927016, 30.319184, "Saint Petersburg"),
    Place(4,"ГУАП", "Чесменский дворец, страшное место, не советуем туда ходить", 0, "img4", 59.857597, 30.327792, "Saint Petersburg"),
)