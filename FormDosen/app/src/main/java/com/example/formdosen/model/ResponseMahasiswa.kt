package com.example.formdosen.model

import com.google.gson.annotations.SerializedName

// Model untuk Response API Mahasiswa
data class ResponseMahasiswa(
    @SerializedName("kode")
    val kode: Int,

    @SerializedName("pesan")
    val pesan: String,

    @SerializedName("data")
    val data: List<DataMahasiswa>? = null
)

// Model untuk Data Mahasiswa
data class DataMahasiswa(
    @SerializedName("nim")
    val nim: String,

    @SerializedName("nama_lengkap")
    val namaLengkap: String,

    @SerializedName("jenis_kelamin")
    val jenisKelamin: String,

    @SerializedName("nama_prodi")
    val namaProdi: String
)