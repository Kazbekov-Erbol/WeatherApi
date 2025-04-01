package com.example.weatherapi.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rest_api_weather.Repository.WeatherRepository
import com.example.rest_api_weather.model.models.WeatherRespons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    // LiveData для обновления UI
    private val _weatherData = MutableLiveData<WeatherRespons>()
    val weatherData: LiveData<WeatherRespons> get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Метод для загрузки данных о погоде
    fun loadWeather(location: String) {
        scope.launch {
            try {
                val weatherResponse = repository.getCurrentWeather(location)
                _weatherData.value = weatherResponse
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel() // Очистка ресурсов при уничтожении ViewModel
    }
}
