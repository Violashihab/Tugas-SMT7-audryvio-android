package com.latihan.androiddasar

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CheckBoxActivity : AppCompatActivity() {
    private val selectedHobbies = mutableListOf<String>()
    private val selectedLanguages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_check_box)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupCheckBoxes()
    }

    private fun setupCheckBoxes() {
        // Deklarasi CheckBox untuk Hobi
        val cbReading = findViewById<CheckBox>(R.id.cb_reading)
        val cbSports = findViewById<CheckBox>(R.id.cb_sports)
        val cbMusic = findViewById<CheckBox>(R.id.cb_music)
        val cbTraveling = findViewById<CheckBox>(R.id.cb_traveling)
        val cbCoding = findViewById<CheckBox>(R.id.cb_coding)

        // Deklarasi CheckBox untuk Bahasa Pemrograman
        val cbKotlin = findViewById<CheckBox>(R.id.cb_kotlin)
        val cbJava = findViewById<CheckBox>(R.id.cb_java)
        val cbPython = findViewById<CheckBox>(R.id.cb_python)
        val cbJavascript = findViewById<CheckBox>(R.id.cb_javascript)

        // Tombol dan TextView
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvResult = findViewById<TextView>(R.id.tv_result)
        val btnSelectAll = findViewById<Button>(R.id.btn_select_all)
        val btnClearAll = findViewById<Button>(R.id.btn_clear_all)

        // Listeners untuk Hobi
        val hobbyCheckBoxes = listOf(cbReading, cbSports, cbMusic, cbTraveling, cbCoding)
        hobbyCheckBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val hobby = checkBox.text.toString()
                if (isChecked) {
                    selectedHobbies.add(hobby)
                    checkBox.setTextColor(Color.GREEN)
                } else {
                    selectedHobbies.remove(hobby)
                    checkBox.setTextColor(Color.BLACK)
                }
                updateHobbySummary()
            }
        }

        // Listeners untuk Bahasa Pemrograman
        val languageCheckBoxes = listOf(cbKotlin, cbJava, cbPython, cbJavascript)
        languageCheckBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val language = checkBox.text.toString()
                if (isChecked) {
                    selectedLanguages.add(language)
                } else {
                    selectedLanguages.remove(language)
                }
                updateLanguageSummary()
            }
        }

        // Submit Button
        btnSubmit.setOnClickListener {
            val result = """
                HASIL PILIHAN:
                
                Hobi yang dipilih:
                ${selectedHobbies.joinToString("\n") { "• $it" }}
                
                Bahasa Pemrograman yang dikuasai:
                ${selectedLanguages.joinToString("\n") { "• $it" }}
                
                Total Pilihan:
                - Hobi: ${selectedHobbies.size}
                - Bahasa: ${selectedLanguages.size}
            """.trimIndent()

            tvResult.text = result
        }

        // Select All Button
        btnSelectAll.setOnClickListener {
            hobbyCheckBoxes.forEach { it.isChecked = true }
            languageCheckBoxes.forEach { it.isChecked = true }
            showToast("Semua item dipilih")
        }

        // Clear All Button
        btnClearAll.setOnClickListener {
            hobbyCheckBoxes.forEach { it.isChecked = false }
            languageCheckBoxes.forEach { it.isChecked = false }
            selectedHobbies.clear()
            selectedLanguages.clear()
            tvResult.text = "Hasil akan ditampilkan di sini..."
            showToast("Semua pilihan direset")
        }

        // Toggle Button untuk CheckBox State
        val btnToggleState = findViewById<Button>(R.id.btn_toggle_state)
        btnToggleState.setOnClickListener {
            val isEnabled = cbReading.isEnabled
            hobbyCheckBoxes.forEach { it.isEnabled = !isEnabled }
            languageCheckBoxes.forEach { it.isEnabled = !isEnabled }

            val state = if (!isEnabled) "diaktifkan" else "dinonaktifkan"
            btnToggleState.text = if (!isEnabled) "DISABLE CHECKBOX" else "ENABLE CHECKBOX"
            showToast("CheckBox $state")
        }
    }

    private fun updateHobbySummary() {
        val tvHobbySummary = findViewById<TextView>(R.id.tv_hobby_summary)
        tvHobbySummary.text = "Hobi terpilih: ${selectedHobbies.size}"
    }

    private fun updateLanguageSummary() {
        val tvLanguageSummary = findViewById<TextView>(R.id.tv_language_summary)
        tvLanguageSummary.text = "Bahasa terpilih: ${selectedLanguages.size}"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}