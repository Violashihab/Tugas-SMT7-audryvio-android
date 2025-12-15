package com.example.darkmodeapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // mendeklarasi UI
    private lateinit var rootLayout: LinearLayout
    private lateinit var switchModeGelap: Switch
    private lateinit var textSalam: TextView
    private lateinit var textStatusMode: TextView
    private lateinit var editTextContoh: EditText


    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "dark_mode_pref"
    private val KEY_DARK_MODE = "is_dark_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        initViews()

        loadDarkModeState()


        setupSwitchListener()
    }

    private fun initViews() {
        rootLayout = findViewById(R.id.root_layout)
        switchModeGelap = findViewById(R.id.switch_mode_gelap)
        textSalam = findViewById(R.id.text_salam)
        textStatusMode = findViewById(R.id.text_status_mode)
        editTextContoh = findViewById(R.id.edit_text_contoh)
    }

    private fun loadDarkModeState() {
        // Baca status dari Shared Preferences (default: false = Light Mode)
        val isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false)


        switchModeGelap.isChecked = isDarkMode

        applyDarkMode(isDarkMode)
    }

    private fun setupSwitchListener() {
        switchModeGelap.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_DARK_MODE, isChecked)
            editor.apply()

            applyDarkMode(isChecked)

            val mode = if (isChecked) "Gelap" else "Terang"
            android.widget.Toast.makeText(
                this,
                "Mode $mode diaktifkan",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun applyDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            rootLayout.setBackgroundColor(Color.parseColor("#121212")) // Dark gray
            textSalam.setTextColor(Color.WHITE)
            textStatusMode.setTextColor(Color.LTGRAY)
            textStatusMode.text = "(Mode Gelap)"
            editTextContoh.setTextColor(Color.WHITE)
            editTextContoh.setHintTextColor(Color.GRAY)
            editTextContoh.setBackgroundColor(Color.parseColor("#333333"))

            switchModeGelap.setTextColor(Color.WHITE)

        } else {

            rootLayout.setBackgroundColor(Color.WHITE)
            textSalam.setTextColor(Color.BLACK)
            textStatusMode.setTextColor(Color.DKGRAY)
            textStatusMode.text = "(Mode Terang)"
            editTextContoh.setTextColor(Color.BLACK)
            editTextContoh.setHintTextColor(Color.DKGRAY)
            editTextContoh.setBackgroundColor(Color.parseColor("#F0F0F0"))


            switchModeGelap.setTextColor(Color.BLACK)
        }
    }
}