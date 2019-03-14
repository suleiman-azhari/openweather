package com.suleimanazhari.openweather.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.suleimanazhari.openweather.R
import com.suleimanazhari.openweather.data.network.WeatherService
import com.suleimanazhari.openweather.data.network.InternetAvailabilityInterceptorImpl
import com.suleimanazhari.openweather.data.network.NetworkDataSource
import com.suleimanazhari.openweather.data.network.NetworkDataSourceImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WeatherActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var viewModel: WeatherViewModel

    // TODO DI with Dagger2
    private lateinit var apiService: WeatherService
    private lateinit var networkDataSource: NetworkDataSource
    private lateinit var weatherViewModelFactory: WeatherViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService = WeatherService(InternetAvailabilityInterceptorImpl(this))
        networkDataSource = NetworkDataSourceImpl(apiService)
        weatherViewModelFactory = WeatherViewModelFactory(networkDataSource)

        job = Job()

        viewModel = ViewModelProviders.of(this, weatherViewModelFactory)
                .get(WeatherViewModel::class.java)

        bind()
    }

    private fun bind() = launch {
        val temp = viewModel.weather.await()
        temp.observe(this@WeatherActivity, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            showCity(it.name)
            showTemp(it.main.temp)
            showDesc(it.weather[0].description)
            showMinTemp(it.main.tempMin)
            showMaxTemp(it.main.tempMax)
            showIcon(it.weather[0].icon)
        })
    }

    private fun showCity(city: String) {
        textView_city.text = city
    }

    @SuppressLint("SetTextI18n")
    private fun showTemp(temp: Double) {
        textView_temperature.text = "$temp°C"
    }

    private fun showDesc(desc: String) {
        textView_description.text = desc
    }

    @SuppressLint("SetTextI18n")
    private fun showMinTemp(temp: Double) {
        textView_temp_min.text = "Min Temp: $temp°C"
    }

    @SuppressLint("SetTextI18n")
    private fun showMaxTemp(temp: Double) {
        textView_temp_max.text = "Max Temp: $temp°C"
    }

    private fun showIcon(code: String) {
        GlideApp.with(this@WeatherActivity)
                .load("http://openweathermap.org/img/w/$code.png")
                .into(imageView_icon)
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}