package com.example.whereToGo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.whereToGo.model.Place

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: Place)

    @Update
    suspend fun updatePlace(place: Place)

    @Delete
    suspend fun deletePlace(place: Place)

    @Query("DELETE FROM place_table")
    suspend fun deleteAllPlaces()

    @Query("SELECT * FROM place_table ORDER BY id ASC")
    fun getAllPlaces(): LiveData<List<Place>>

}