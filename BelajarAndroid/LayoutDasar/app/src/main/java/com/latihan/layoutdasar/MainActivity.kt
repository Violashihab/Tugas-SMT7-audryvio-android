package com.latihan.layoutdasar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Deklarasi Variabel
    private lateinit var button_login: Button
    private lateinit var button_register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // inisialisasi pada button login dan register
        button_login = findViewById(R.id.buttonLogin)
        button_register = findViewById(R.id.buttonRegister)

        button_login.setOnClickListener {
            // intent untuk pindah ke halaman Login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        button_register.setOnClickListener {
            // intent untuk pindah ke halaman register
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}