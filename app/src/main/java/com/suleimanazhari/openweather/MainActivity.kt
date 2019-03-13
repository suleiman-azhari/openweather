package com.suleimanazhari.openweather

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val apiService = WeatherService()
//
//        GlobalScope.launch(Dispatchers.Main) {
//            val response = apiService.getWeather("cyberjaya").await()
//            textView_temperature.text = response.main.temp.toString()
//        }
    }
}
