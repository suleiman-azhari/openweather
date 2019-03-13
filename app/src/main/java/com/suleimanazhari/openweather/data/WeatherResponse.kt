package com.suleimanazhari.openweather.data

data class WeatherResponse(
        val id: Int,
        val main: Main,
        val name: String,
        val weather: List<Weather>
)