package com.example.formdosen.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // SESUAIKAN DENGAN IP LAPTOP ANDA
    // Untuk emulator: http://10.0.2.2/
    // Untuk device fisik: http://192.168.x.x/
    private const val BASE_URL = "http://10.0.2.2/api_kampus/"

    // Membuat instance Retrofit menggunakan lazy initialization
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}