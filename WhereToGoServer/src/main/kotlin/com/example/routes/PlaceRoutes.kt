package com.example.routes

import com.example.database.PlaceDao
import com.example.models.Place
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.placesRouting() {

    val placeDao = PlaceDao()

    route("/places") {

        get {

            val city = call.request.queryParameters["city"]
            val places =
                if (city != null)
                    placeDao.getPlacesFromCity(city)
                else
                    placeDao.getAll()

            if (places.isNotEmpty()) call.respond(places)
            else call.respondText("No places found", status = HttpStatusCode.NotFound)
        }

        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            try { id.toInt() } catch (e: Exception) { return@get call.respondText(
                "No place with id $id",
                status = HttpStatusCode.BadRequest)
            }
            val place = placeDao.get(id.toInt()) ?: return@get call.respondText(
                "No place with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(place)
        }

        post {
            val place = call.receive<Place>()
            placeDao.add(place)
            call.respondText("Place stored correctly", status = HttpStatusCode.Created)

        }

        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            try { id.toInt() } catch (e: Exception) { return@delete call.respondText(
                "No place with id $id",
                status = HttpStatusCode.BadRequest)
            }
            if (placeDao.delete(id.toInt())) {
                call.respondText("Place removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun Application.registerPlaceRoutes() {
    routing {
        placesRouting()
    }
}

