package com.example.rest_api_weather.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rest_api_weather.model.models.WeatherRespons
import com.example.rest_api_weather.presenter.WeatherContract
import com.example.rest_api_weather.presenter.WeatherPresenter
import com.example.weatherapi.R
import com.example.weatherapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WeatherContract.View {

    private lateinit var binding: ActivityMainBinding

    private val presenter by lazy { WeatherPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListener()

    }
    fun setupClickListener(){
        //spinner
        val cities = listOf("Bishkek", "London", "Dubai", "Tokyo", "Paris","Moscow")
        presenter.loadData("Bishkek")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val city = cities[position]
                presenter.loadData( city)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun showWeather(weatherResponse: WeatherRespons) {
        binding.apply {
            textView.text = "${weatherResponse.current?.tempC.toString()}°"

            val condition = weatherResponse.current?.condition?.text ?: ""
            Toast.makeText(this@MainActivity, "Condition: $condition", Toast.LENGTH_SHORT).show()
            val imageRes = when {
                condition.contains("sun") || condition.contains("clear") -> R.drawable.ic_sun
                condition.contains("cloud") || condition.contains("partly")|| condition.contains("drizzle") -> R.drawable.ic_cloud
                condition.contains("rain") -> R.drawable.ic_cloud
                else -> R.drawable.ic_sun
            }
        imgWeather.setImageResource(imageRes)
            val cond = if (condition.contains("clear") || condition.contains("sun")) {
                R.drawable.color_light_bacground
            } else {
                R.drawable.color_bacgr
            }
           main.setBackgroundResource(cond)
        }
        binding.osadki.text = weatherResponse.current?.condition?.text ?: ""
        binding.rainPre.text = "${weatherResponse.current?.precipMm.toString()}%"
        binding.humidity.text = "${weatherResponse.current?.humidity.toString()}%"
        binding.wind.text = "${weatherResponse.current?.windKph.toString()}km/h"
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}