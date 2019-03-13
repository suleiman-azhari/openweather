package com.suleimanazhari.openweather.data

import com.google.gson.annotations.SerializedName

data class Main(
        val temp: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("temp_min")
        val tempMin: Double
)