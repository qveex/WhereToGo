package com.example.routes

import com.example.database.PlaceDao
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.cityRouting() {

    val placeDao = PlaceDao()

    route("/city") {

        get {
            val places = placeDao.getAll()
            if (places.isNotEmpty()) call.respond(places)
            else call.respondText("No places found", status = HttpStatusCode.NotFound)
        }

        get("{city}") {
            val city = call.parameters["city"] ?: return@get call.respondText(
                "Missing or malformed city",
                status = HttpStatusCode.BadRequest
            )

            val places = placeDao.getPlacesFromCity(city)
            if (places.isEmpty()) return@get call.respondText(
                "No places in $city",
                status = HttpStatusCode.NotFound
            )

            call.respond(places)
        }

    }
}

fun Application.registerCityRoutes() {
    routing {
        cityRouting()
    }
}