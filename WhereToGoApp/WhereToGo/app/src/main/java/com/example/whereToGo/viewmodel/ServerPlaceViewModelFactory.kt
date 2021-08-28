package com.example.whereToGo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.whereToGo.repository.ServerPlaceRepository

class ServerPlaceViewModelFactory(
    private val repository: ServerPlaceRepository
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceViewModelServer(repository) as T
    }
}