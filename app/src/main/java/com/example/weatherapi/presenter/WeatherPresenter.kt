package com.example.rest_api_weather.presenter

import com.example.rest_api_weather.Repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherPresenter(private var view: WeatherContract.View?) : WeatherContract.Presenter {

    private val repository = WeatherRepository()

    private val job = Job()

    private val scope = CoroutineScope(Dispatchers.Main + job)


    override fun loadData(location: String) {
        scope.launch {
            try {
                val weatherResponse = repository.getCurrentWeather(location)

                view?.showWeather(weatherResponse)
            } catch (e: Exception) {
                view?.showError(e.message ?: "Unknown error")
            }
        }
    }

    override fun onDestroy() {
        view = null

        job.cancel()
    }
}