package com.example.formdosen.network

import com.example.formdosen.model.ResponseDosen
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("insert_dosen.php") // Sesuaikan dengan nama file PHP Anda
    fun insertDosen(
        @Field("nidn") nidn: String,
        @Field("nama_dosen") nama: String,
        @Field("jabatan") jabatan: String
    ): Call<ResponseDosen>

    // Anda bisa menambah fungsi lain di sini (get_dosen, delete_dosen, dll)
}
