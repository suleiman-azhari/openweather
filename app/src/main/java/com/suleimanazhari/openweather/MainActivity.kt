package com.suleimanazhari.openweather

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.suleimanazhari.openweather.data.NetworkDataSourceImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val apiService = WeatherService()
//        val networkDataSource = NetworkDataSourceImpl(apiService)
//
//        networkDataSource.downloadedWeather.observe(this, Observer {
//            textView_temperature.text = it.main.temp.toString()
//        })
//
//        GlobalScope.launch(Dispatchers.Main) {
//            networkDataSource.fetchWeather("cyberjaya")
//        }
    }
}
