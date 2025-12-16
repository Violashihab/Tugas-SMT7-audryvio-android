package com.example.cirebonweather.model

import com.google.gson.annotations.SerializedName

// wadah utama
data class WeatherResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("hourly") val hourly: HourlyData
)

// Data per jam
data class HourlyData(
    @SerializedName("time") val time: List<String>,
    @SerializedName("temperature_2m") val temperature: List<Double>
)

// model untuk ditampilkan di list
data class WeatherItem(
    val time: String,
    val temperature: String
)