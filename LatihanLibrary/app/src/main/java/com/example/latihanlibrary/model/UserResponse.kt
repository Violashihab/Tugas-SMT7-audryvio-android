package com.example.latihanlibrary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(
    @SerializedName("data") val data: List<User>
)

@Parcelize
data class User(
    val id: Int,
    val email: String,
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String,
    val avatar: String
) : Parcelable
