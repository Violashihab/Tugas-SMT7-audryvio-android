package com.example.latihanlibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanlibrary.adapter.UserAdapter
import com.example.latihanlibrary.model.UserResponse
import com.example.latihanlibrary.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_users)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userAdapter = UserAdapter(listOf()) { user ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("USER_DATA", user)
            startActivity(intent)
        }
        recyclerView.adapter = userAdapter

        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener {
            currentPage = if (currentPage == 1) 2 else 1
            btnNext.text = "Page: $currentPage (Klik Ganti)"
            getDataFromApi(currentPage)
        }

        getDataFromApi(currentPage)
    }

    private fun getDataFromApi(page: Int) {
        ApiClient.instance.getUsers(page).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.data
                    if (users != null && users.isNotEmpty()) {
                        userAdapter.setData(users)
                    } else {
                        // Jika data kosong
                        Toast.makeText(this@MainActivity, "Data dari server kosong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Jika server merespon tapi error (contoh: 404)
                    Toast.makeText(this@MainActivity, "Server Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // JIKA BAGIAN INI MUNCUL, BERARTI MASALAHNYA KONEKSI
                Toast.makeText(this@MainActivity, "Koneksi Gagal: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}
