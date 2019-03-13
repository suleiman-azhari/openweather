package com.suleimanazhari.openweather.data

import android.util.Log
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
        try {
            val fetchedWeather = weatherService
                    .getWeather(location).await()

            mutableDownloadedWeather.postValue(fetchedWeather)
        } catch (e: NoInternetException) {
            Log.d("Network", "No Internet!")
        }
    }
}