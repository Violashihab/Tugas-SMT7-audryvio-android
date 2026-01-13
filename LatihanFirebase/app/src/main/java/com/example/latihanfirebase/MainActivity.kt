package com.example.latihanfirebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // UI Components
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var btnLogout: Button
    private lateinit var btnGetToken: Button
    private lateinit var tvStatus: TextView
    private lateinit var tvToken: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Inisialisasi View berdasarkan ID di XML
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)
        btnLogout = findViewById(R.id.btn_logout)
        btnGetToken = findViewById(R.id.btn_get_token)
        tvStatus = findViewById(R.id.tv_status)
        tvToken = findViewById(R.id.tv_token)
        progressBar = findViewById(R.id.progress_bar)

        // Cek status login saat awal aplikasi dibuka
        updateUI()

        // Logika Register
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                            updateUI()
                        } else {
                            Toast.makeText(this, "Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Isi email dan password", Toast.LENGTH_SHORT).show()
            }
        }

        // Logika Login
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                            updateUI()
                        } else {
                            Toast.makeText(this, "Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        // Logika Logout
        btnLogout.setOnClickListener {
            auth.signOut()
            updateUI()
            Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
        }

        // Logika Ambil Token FCM
        btnGetToken.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM_ERROR", "Fetching FCM token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                tvToken.visibility = View.VISIBLE
                tvToken.text = "Token: $token"
                Log.d("FCM_TOKEN", token)
            }
        }
    }

    private fun updateUI() {
        val user = auth.currentUser
        if (user != null) {
            tvStatus.text = "Status: Login sebagai ${user.email}"
            btnLogout.visibility = View.VISIBLE
            btnRegister.visibility = View.GONE
            btnLogin.visibility = View.GONE
        } else {
            tvStatus.text = "Status: Belum Login"
            btnLogout.visibility = View.GONE
            btnRegister.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            tvToken.visibility = View.GONE
        }
    }
}