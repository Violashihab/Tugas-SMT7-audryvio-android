package com.example.luaspersegipanjang

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editTextPanjangPersegi = findViewById<EditText>(R.id.edit_text_panjang_persegi)
        val editTextLebarPersegi = findViewById<EditText>(R.id.edit_text_lebar_persegi)
        val buttonHitungLuas = findViewById<Button>(R.id.button_hitung_luas)
        val textViewHasilLuas = findViewById<TextView>(R.id.text_view_hasil_luas)


        buttonHitungLuas.setOnClickListener {
            // mengambil/get dari edit text
            val inputPanjang = editTextPanjangPersegi.text.toString()
            val inputLebar = editTextLebarPersegi.text.toString()


            if (inputPanjang.isEmpty() || inputLebar.isEmpty()) {
                Toast.makeText(this, "Harap isi panjang dan lebar!", Toast.LENGTH_SHORT).show()
            } else {

                val panjang = inputPanjang.toDouble()
                val lebar = inputLebar.toDouble()

                val luas = panjang * lebar

                textViewHasilLuas.text = "Luas: $luas"
            }
        }
    }
}