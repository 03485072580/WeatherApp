package com.app.weatherapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weatherapp.WeatherAPI
import com.app.weatherapp.models.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue

class WeatherViewModel: ViewModel() {
    val weatherData = MutableStateFlow<WeatherResponse?>(null);
    val weatherAPI = WeatherAPI.create()
    var isLoading by mutableStateOf(false)

    fun fetchWeather(city:String, apiKey:String){
        viewModelScope.launch {
            try{
                isLoading = true
                val response = weatherAPI.getWeather(city, apiKey)
                weatherData.value = response
            }catch (exc:Exception){
                exc.printStackTrace()
            }finally {
                isLoading = false
            }
        }
    }

}