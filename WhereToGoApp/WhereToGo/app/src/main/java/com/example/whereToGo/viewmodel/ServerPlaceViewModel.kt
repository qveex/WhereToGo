package com.example.whereToGo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whereToGo.model.Place
import com.example.whereToGo.repository.ServerPlaceRepository
import kotlinx.coroutines.launch

class ServerPlaceViewModel(private val repository: ServerPlaceRepository): ViewModel() {

    val myResponse: MutableLiveData<List<Place>> = MutableLiveData()

    fun getPlaces() {
        viewModelScope.launch {
            val response = repository.getPlaces()
            myResponse.value = response
        }
    }
}