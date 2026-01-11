package com.example.formdosen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Gunakan cara manual

        val btnDosen = findViewById<Button>(R.id.btnToDosen)
        btnDosen.setOnClickListener {
            val intent = Intent(this, FormDosenActivity::class.java)
            startActivity(intent)
        }
    }
}
