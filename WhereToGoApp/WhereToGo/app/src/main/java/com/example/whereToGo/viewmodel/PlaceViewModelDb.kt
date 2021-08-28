package com.example.whereToGo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.whereToGo.data.PlaceDatabase
import com.example.whereToGo.model.Place
import com.example.whereToGo.repository.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceViewModelDb(application: Application): AndroidViewModel(application) {

    val getAllData: LiveData<List<Place>>
    private val repository: PlaceRepository

    init {
        val placeDao = PlaceDatabase.getDatabase(application).placeDao()
        repository = PlaceRepository(placeDao)
        getAllData = repository.getAllData
    }

    fun addPlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlace(place)
        }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePlace(place)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlace(place)
        }
    }

    fun deleteAllPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPlaces()
        }
    }

    fun addPlaces(places: List<Place>) {
        viewModelScope.launch(Dispatchers.IO) {
            places.forEach { repository.addPlace(it) }
        }
    }

}