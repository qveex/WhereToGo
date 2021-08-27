package com.example

import com.example.routes.registerCityRoutes
import com.example.routes.registerPlaceRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database

// https://github.com/ktorio/ktor-http-api-sample/tree/final
// https://ktor.io/docs/creating-http-apis.html#defining-the-routing-for-customers
// https://ktor.io/docs/requests.html#query_parameters


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {

    Database.connect("jdbc:h2:./Places", "org.h2.Driver")

    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        anyHost()
    }
    registerPlaceRoutes()
    registerCityRoutes()
}