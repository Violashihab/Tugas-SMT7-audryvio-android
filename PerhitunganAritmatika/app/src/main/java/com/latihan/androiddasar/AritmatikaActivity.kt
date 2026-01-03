package com.latihan.androiddasar

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AritmatikaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aritmatika)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAwal()
    }

    private fun setupAwal() {
        val editTextBil1 = findViewById<EditText>(R.id.edit_text_bil1)
        val editTextBil2 = findViewById<EditText>(R.id.edit_text_bil2)
        val buttonTambah = findViewById<Button>(R.id.button_tambah)
        val buttonPerkalian = findViewById<Button>(R.id.button_perkalian)
        val buttonPembagian = findViewById<Button>(R.id.button_pembagian)
        val buttonKurang = findViewById<Button>(R.id.button_kurang)
        val textViewHasil = findViewById<TextView>(R.id.text_viewhasil)


        editTextBil1.setText("0")
        editTextBil2.setText("0")


        buttonTambah.setOnClickListener {
            val InputBil1String = editTextBil1.text.toString().trim()
            val InputBil2String = editTextBil2.text.toString().trim()

            val bil1: Double = InputBil1String.toDoubleOrNull() ?: 0.0
            val bil2: Double = InputBil2String.toDoubleOrNull() ?: 0.0

            val hasil: Double = bil1 + bil2
            textViewHasil.setText("Hasil Penjumlahan = $hasil")
        }

        buttonKurang.setOnClickListener {
            val InputBil1String = editTextBil1.text.toString().trim()
            val InputBil2String = editTextBil2.text.toString().trim()

            val bil1: Double = InputBil1String.toDoubleOrNull() ?: 0.0
            val bil2: Double = InputBil2String.toDoubleOrNull() ?: 0.0

            val hasil: Double = bil1 - bil2
            textViewHasil.setText("Hasil Pengurangan = $hasil")

        }

        buttonPerkalian.setOnClickListener {
            val InputBil1String = editTextBil1.text.toString().trim()
            val InputBil2String = editTextBil2.text.toString().trim()

            val bil1: Double = InputBil1String.toDoubleOrNull() ?: 0.0
            val bil2: Double = InputBil2String.toDoubleOrNull() ?: 0.0

            val hasil: Double = bil1 * bil2
            textViewHasil.setText("Hasil Perkalian = $hasil")

        }

        buttonPembagian.setOnClickListener {
            val InputBil1String = editTextBil1.text.toString().trim()
            val InputBil2String = editTextBil2.text.toString().trim()

            val bil1: Double = InputBil1String.toDoubleOrNull() ?: 0.0
            val bil2: Double = InputBil2String.toDoubleOrNull() ?: 0.0
            val hasil: Double = bil1 / bil2
            textViewHasil.setText("Hasil Pembagian = $hasil")

        }

    }
}