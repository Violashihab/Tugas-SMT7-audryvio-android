package com.latihan.androiddasar

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RadioButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_radio_button)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRadioButtons()
    }

    private fun setupRadioButtons() {
        val radioGroupJenisKelamin = findViewById<RadioGroup>(R.id.radioGroupJenisKelamin)
        val radioGroupJurusan = findViewById<RadioGroup>(R.id.radioGroupJurusan)
        val radioGroupKategori = findViewById<RadioGroup>(R.id.radioGroupKategori)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvResult = findViewById<TextView>(R.id.tv_result)

        // Single Selection - Jenis Kelamin
        radioGroupJenisKelamin.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_pria -> showToast("Jenis Kelamin: Pria dipilih")
                R.id.radio_wanita -> showToast("Jenis Kelamin: Wanita dipilih")
            }
        }

        // Submit Button untuk mengambil semua pilihan
        btnSubmit.setOnClickListener {
            val selectedJenisKelamin = when (radioGroupJenisKelamin.checkedRadioButtonId) {
                R.id.radio_pria -> "Pria"
                R.id.radio_wanita -> "Wanita"
                else -> "Belum dipilih"
            }

            val selectedJurusan = when (radioGroupJurusan.checkedRadioButtonId) {
                R.id.radio_ti -> "Teknik Informatika"
                R.id.radio_si -> "Sistem Informasi"
                R.id.radio_dkv -> "Desain Komunikasi Visual"
                else -> "Belum dipilih"
            }

            val selectedKategori = when (radioGroupKategori.checkedRadioButtonId) {
                R.id.radio_mahasiswa -> "Mahasiswa"
                R.id.radio_dosen -> "Dosen"
                R.id.radio_staff -> "Staff"
                else -> "Belum dipilih"
            }

            val result = """
                Hasil Pilihan:
                Jenis Kelamin: $selectedJenisKelamin
                Jurusan: $selectedJurusan
                Kategori: $selectedKategori
            """.trimIndent()

            tvResult.text = result
        }

        // Dynamic RadioButton - Tambah opsi baru
        val btnAddOption = findViewById<Button>(R.id.btn_add_option)
        val radioGroupDynamic = findViewById<RadioGroup>(R.id.radioGroupDynamic)

        btnAddOption.setOnClickListener {
            val newOption = "Opsi ${radioGroupDynamic.childCount + 1}"
            val radioButton = RadioButton(this).apply {
                text = newOption
                id = View.generateViewId()
                textSize = 14f
                setPadding(20, 20, 20, 20)
            }
            radioGroupDynamic.addView(radioButton)
            showToast("Opsi baru ditambahkan: $newOption")
        }

        // Clear Selection
        val btnClear = findViewById<Button>(R.id.btn_clear)
        btnClear.setOnClickListener {
            radioGroupJenisKelamin.clearCheck()
            radioGroupJurusan.clearCheck()
            radioGroupKategori.clearCheck()
            radioGroupDynamic.clearCheck()
            tvResult.text = "Hasil akan ditampilkan di sini..."
            showToast("Semua pilihan direset")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}