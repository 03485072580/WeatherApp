package com.app.weatherapp

import com.app.weatherapp.models.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city:String,
        @Query("appid") apiKey:String = "YOUR_API_KEY",
        @Query("units") units:String = "metric"
    ):WeatherResponse

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        fun create():WeatherAPI{
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WeatherAPI::class.java)
        }
    }
}