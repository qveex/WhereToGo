package com.example

import com.example.routes.registerCityRoutes
import com.example.routes.registerPlaceRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

// https://github.com/ktorio/ktor-http-api-sample/tree/final
// https://ktor.io/docs/creating-http-apis.html#defining-the-routing-for-customers


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {

    //Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
    Database.connect("jdbc:h2:./Places", "org.h2.Driver")

    transaction {
        //SchemaUtils.create(Places)

        /*Places.insert {
            it[name] = "Парк"
            it[description] = "Небольшой зеленый парк со скамейками"
            it[visitCounter] = 12
            it[image] = "img1"
            it[latitude] = 59.893228
            it[longitude] = 30.417227
            it[city] = "Saint Petersburg"
        }*/
    }

    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        anyHost()
    }
    registerPlaceRoutes()
    registerCityRoutes()
}