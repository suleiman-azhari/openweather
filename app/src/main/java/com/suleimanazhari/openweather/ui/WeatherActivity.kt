package com.suleimanazhari.openweather.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
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

            group_loading.visibility = View.GONE
            showCity(it.name)
            showTemp(it.main.temp)
            showDesc(it.weather[0].description)
            showMinTemp(it.main.tempMin)
            showMaxTemp(it.main.tempMax)
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
        textView_temp_min.text = "Minimum Temperature: $temp°C"
    }

    @SuppressLint("SetTextI18n")
    private fun showMaxTemp(temp: Double) {
        textView_temp_max.text = "Maximum Temperature: $temp°C"
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}