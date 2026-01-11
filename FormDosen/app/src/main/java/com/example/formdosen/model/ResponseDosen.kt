package com.example.formdosen.model

import com.google.gson.annotations.SerializedName

// 1. Response utama dari API
data class ResponseDosen(
    @SerializedName("kode")
    val kode: Int,

    @SerializedName("pesan")
    val pesan: String,

    @SerializedName("data")
    val data: List<DataDosen>? = null  // Bisa null jika tidak ada data
)

// 2. Model untuk satu data dosen
data class DataDosen(
    @SerializedName("nidn")
    val nidn: String,

    @SerializedName("nama_dosen")
    val namaDosen: String,

    @SerializedName("jabatan")
    val jabatan: String
)