package com.example.database

import com.example.models.Place
import io.ktor.util.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PlaceDao {

    suspend fun add(place: Place) {

        newSuspendedTransaction {
            Places.insert {
                it[name] = place.name
                it[description] = place.description
                it[visitCounter] = place.visitCounter
                it[image] = place.image
                it[latitude] = place.latitude
                it[longitude] = place.longitude
                it[city] = place.city
            }
        }
    }

    suspend fun getAll() = newSuspendedTransaction {
        return@newSuspendedTransaction Places.selectAll().map { Places.toPlace(it) }
    }

    suspend fun get(id: Int) = newSuspendedTransaction {
        val p = Places.select { Places.id eq id }.map { Places.toPlace(it) }
        if (p.isEmpty()) return@newSuspendedTransaction null
        else return@newSuspendedTransaction p
    }

    suspend fun getPlacesFromCity(city: String) = newSuspendedTransaction {
        return@newSuspendedTransaction Places.select { Places.city eq city }.map { Places.toPlace(it) }
    }

    suspend fun delete(id: Int) = newSuspendedTransaction {
            val p = Places.select { Places.id eq id }.map { Places.toPlace(it) }
            if (p.isEmpty()) return@newSuspendedTransaction false
            Places.deleteWhere { Places.id eq id }
            return@newSuspendedTransaction true
        }

    suspend fun update(id: Int, newPlace: Place) {
        newSuspendedTransaction {
            Places.update({ Places.id eq id }) { p ->
                p[name] = newPlace.name
                p[description] = newPlace.description
                p[visitCounter] = newPlace.visitCounter
                p[image] = newPlace.image
                p[latitude] = newPlace.latitude
                p[longitude] = newPlace.longitude
                p[city] = newPlace.city
            }
        }
    }
}