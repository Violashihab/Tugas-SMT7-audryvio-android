package com.example.latihanlibrary.network


import com.example.latihanlibrary.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/users")
    fun getUsers(@Query("page") page: Int): Call<UserResponse>
    fun getUsers()
}