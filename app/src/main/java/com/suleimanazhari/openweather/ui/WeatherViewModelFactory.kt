package com.suleimanazhari.openweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suleimanazhari.openweather.data.NetworkDataSource

class WeatherViewModelFactory(
        private val networkDataSource: NetworkDataSource
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(networkDataSource) as T
    }
}