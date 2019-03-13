package com.suleimanazhari.openweather.ui

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.suleimanazhari.openweather.R
import com.suleimanazhari.openweather.WeatherService
import com.suleimanazhari.openweather.data.InternetAvailabilityInterceptorImpl
import com.suleimanazhari.openweather.data.NetworkDataSourceImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WeatherActivity : AppCompatActivity(), CoroutineScope {
    
    private lateinit var job: Job
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO DI with Dagger2
        val apiService = WeatherService(InternetAvailabilityInterceptorImpl(this))
        val networkDataSource = NetworkDataSourceImpl(apiService)
        val weatherViewModelFactory = WeatherViewModelFactory(networkDataSource)

        job = Job()

        viewModel = ViewModelProviders.of(this, weatherViewModelFactory)
                .get(WeatherViewModel::class.java)

        bind()
    }

    private fun bind() = launch {
        val temp = viewModel.weather.await()
        temp.observe(this@WeatherActivity, Observer {
            if (it == null) return@Observer

            textView_temperature.text = it.main.temp.toString()
        })
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}