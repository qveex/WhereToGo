package com.example.database

import com.example.models.Place
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

// https://github.com/JetBrains/Exposed/wiki/DAO#read

object Places : Table() {
    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val visitCounter = integer("visitCounter")
    val image = varchar("image", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val city = varchar("city", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_Place_Id")

    fun toPlace(row: ResultRow): Place =
        Place(
            id = row[id],
            name = row[name],
            description = row[description],
            visitCounter = row[visitCounter],
            image = row[image],
            latitude = row[latitude],
            longitude = row[longitude],
            city = row[city]
        )
}