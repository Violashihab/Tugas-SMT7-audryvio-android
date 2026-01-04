package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnBack: Button

    private val validUsername = "admin"
    private val validPassword = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnBack = findViewById(R.id.btnBack)


        setupButtonListeners()


        etUsername.setText("")
        etPassword.setText("")
    }

    private fun setupButtonListeners() {
        btnLogin.setOnClickListener {
            loginUser()
        }


        btnBack.setOnClickListener {
            goToMenuAsGuest()
        }
    }

    private fun loginUser() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()


        if (username.isEmpty()) {
            etUsername.error = "Username tidak boleh kosong"
            etUsername.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Password tidak boleh kosong"
            etPassword.requestFocus()
            return
        }


        if (username == validUsername && password == validPassword) {
            showToast("Login berhasil!")


            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
            finish()
        } else {
            showToast("Username atau password salah!")
        }
    }

    private fun goToMenuAsGuest() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("GUEST_MODE", true)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}