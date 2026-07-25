package com.app.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.weatherapp.ui.theme.WeatherAppTheme
import com.app.weatherapp.viewmodels.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherScreen()
        }
    }
}

@Composable
fun WeatherScreen() {
    val weatherViewModel:WeatherViewModel = viewModel()
    val weatherData by weatherViewModel.weatherData.collectAsState()
    var city by remember {
        mutableStateOf("")
    }
    val apiKey = "103bb6becab2f4ca3029775de933dcae"



    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Weather App", fontSize = 35.sp)
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                onValueChange = {city = it},
                value = city,
                label = {
                    Text(text = "Enter city name")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            Button(onClick = {
                weatherViewModel.fetchWeather(city,apiKey)
            }) {
                Text("Check Weather")
            }

             if(weatherViewModel.isLoading){
                 Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .weight(1f),
                     contentAlignment = Alignment.Center
                 ) {
                     CircularProgressIndicator(color = Color.Blue)
                 }
             }else{
                weatherData?.let {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherCard("City", it.name, icon = Icons.Default.Place)
                        WeatherCard("Temperature", "${it.main.temp} °C", icon = Icons.Default.Star)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherCard(
                            "Humidity",
                            "${it.main.humidity}%",
                            icon = Icons.Default.Warning
                        )
                        WeatherCard(
                            "Description",
                            it.weather[0].description,
                            icon = Icons.Default.Info
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherCard(label: String, value: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row() {
                Icon(imageVector = icon, contentDescription = null, tint = Color.Blue)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = label, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(text = value, fontSize = 24.sp)
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    WeatherAppTheme {
        WeatherScreen()
    }
}