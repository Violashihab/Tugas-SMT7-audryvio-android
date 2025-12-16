package com.example.cirebonweather

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cirebonweather.model.WeatherItem
import com.example.cirebonweather.model.WeatherResponse
import com.example.cirebonweather.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_cuaca)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ambilDataCuaca()
    }

    private fun ambilDataCuaca() {
        ApiClient.instance.getCuacaCirebon().enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    val hourlyData = body?.hourly

                    // LOGIKA MAPPING:
                    val daftarCuaca = ArrayList<WeatherItem>()
                    hourlyData?.time?.forEachIndexed { index, waktu ->
                        // Ambil suhu di index yang sama
                        val suhu = hourlyData.temperature[index]
                        // Masukkan ke wadah baru
                        daftarCuaca.add(WeatherItem(waktu, "$suhu°C"))
                    }

                    // Pasang ke Adapter
                    recyclerView.adapter = WeatherAdapter(daftarCuaca)
                } else {
                    Toast.makeText(this@MainActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}