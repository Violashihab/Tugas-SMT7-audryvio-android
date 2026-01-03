package com.latihan.androiddasar

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GanjilGenapActivity : AppCompatActivity() {
    private lateinit var editTextNumber : EditText
    private lateinit var buttonCek : Button
    private lateinit var textViewHasil : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ganjil_genap)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextNumber = findViewById(R.id.edit_text_number)
        buttonCek = findViewById(R.id.button_cek)
        textViewHasil = findViewById(R.id.text_view_hasil)

        buttonCek.setOnClickListener {
            val inputString = editTextNumber.text.toString().trim()

            if (inputString.isEmpty()) {
                textViewHasil.text = "Masukkan angka yang valid"
                return@setOnClickListener
            }
            var number = inputString.toIntOrNull()
            if (number == null) {
                textViewHasil.text = "Masukan angka valid"
                return@setOnClickListener
            }
            var hasil = if (number % 2 == 0) {
                "$number adalah bilangan genap."
            } else {
                "$number adalah bilangan ganjil."
            }
            textViewHasil.text = hasil
        }
    }
}