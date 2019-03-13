package com.suleimanazhari.openweather.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suleimanazhari.openweather.WeatherService

class NetworkDataSourceImpl(
        private val weatherService: WeatherService
) : NetworkDataSource {

    private val mutableDownloadedWeather = MutableLiveData<WeatherResponse>()
    override val downloadedWeather: LiveData<WeatherResponse>
        get() = mutableDownloadedWeather

    override suspend fun fetchWeather(location: String) {
        val fetchedWeather = weatherService
                .getWeather(location).await()

        mutableDownloadedWeather.postValue(fetchedWeather)

    }
}