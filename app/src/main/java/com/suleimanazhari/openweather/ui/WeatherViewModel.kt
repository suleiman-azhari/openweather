package com.suleimanazhari.openweather.ui

import androidx.lifecycle.ViewModel
import com.suleimanazhari.openweather.data.NetworkDataSource

class WeatherViewModel(
        private val networkDataSource: NetworkDataSource
) : ViewModel() {
    val weather by lazyDeferred {
        networkDataSource.getWeather("cyberjaya")
    }
}