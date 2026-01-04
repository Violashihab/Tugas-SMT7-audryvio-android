package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvWelcome: TextView = findViewById(R.id.tvWelcome)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        val username = intent.getStringExtra("USERNAME")
        val isGuest = intent.getBooleanExtra("GUEST_MODE", false)

        if (isGuest) {
            tvWelcome.text = "Selamat datang!"
        } else if (username != null) {
            tvWelcome.text = "Selamat datang, $username!"
        } else {
            tvWelcome.text = "Selamat datang!"
        }

        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}