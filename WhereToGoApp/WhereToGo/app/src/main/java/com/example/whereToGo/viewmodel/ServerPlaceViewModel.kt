package com.example.whereToGo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whereToGo.model.Place
import com.example.whereToGo.repository.ServerPlaceRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ServerPlaceViewModel(private val repository: ServerPlaceRepository): ViewModel() {

    val listResponse: MutableLiveData<Response<List<Place>>> = MutableLiveData()
    val singleResponse: MutableLiveData<Response<Place>> = MutableLiveData()

    fun getPlaces() {
        viewModelScope.launch {
            val response = repository.getPlaces()
            listResponse.value = response
        }
    }

    fun getPlace(id: Int) {
        viewModelScope.launch {
            val response = repository.getPlace(id)
            singleResponse.value = response
        }
    }

    fun getCity(city: String) {
        viewModelScope.launch {
            val response = repository.getCity(city)
            listResponse.value = response
        }
    }

    fun createPlace(place: Place) {
        viewModelScope.launch {
            val response = repository.createPlace(place)
            singleResponse.value = response
        }
    }
}