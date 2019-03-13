package com.suleimanazhari.openweather.data

import androidx.lifecycle.LiveData

interface NetworkDataSource {
    val downloadedWeather: LiveData<WeatherResponse>

    suspend fun fetchWeather(
            location: String
    )

    suspend fun getWeather(
            location: String
    ): LiveData<WeatherResponse>
}