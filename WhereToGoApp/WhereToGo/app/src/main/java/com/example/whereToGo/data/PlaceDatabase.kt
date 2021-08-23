package com.example.whereToGo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.whereToGo.utilities.Converters
import com.example.whereToGo.model.Place

@Database(entities = [Place::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlaceDatabase: RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var INSTANCE: PlaceDatabase? = null

        fun getDatabase(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PlaceDatabase::class.java,
                "place_database"
            ).fallbackToDestructiveMigration().build()
    }


}