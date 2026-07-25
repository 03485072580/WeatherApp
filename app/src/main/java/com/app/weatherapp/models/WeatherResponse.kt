package com.app.weatherapp.models

data class WeatherResponse(
    val name:String,
    val weather: List<Weather>,
    val main: Main
)

data class Weather(
    val description:String
)

data class Main(
    val temp:Double,
    val humidity:Int
)
