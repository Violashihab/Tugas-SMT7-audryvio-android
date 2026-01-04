package com.example.loginapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SimpleLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_login)


        val username = findViewById<EditText>(R.id.edittext_username)
        val password = findViewById<EditText>(R.id.edittext_password)
        val btnLogin = findViewById<Button>(R.id.button_simple_login)


        btnLogin.setOnClickListener {
            val inputUsername = username.text.toString()
            val inputPassword = password.text.toString()


            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Username dan Password harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login berhasil! Selamat datang $inputUsername", Toast.LENGTH_SHORT).show()

            }
        }
    }
}