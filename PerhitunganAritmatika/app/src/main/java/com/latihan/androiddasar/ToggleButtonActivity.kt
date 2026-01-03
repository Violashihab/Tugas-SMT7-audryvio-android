package com.latihan.androiddasar

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ToggleButtonActivity : AppCompatActivity() {
    private var isSystemOn = false
    private var isThemeDark = false
    private var isSoundOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_toggle_button)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupToggleButtons()
        setupSwitchButtons()
    }

    private fun setupToggleButtons() {
        val tbBasic = findViewById<ToggleButton>(R.id.tb_basic)
        val tbCustom = findViewById<ToggleButton>(R.id.tb_custom)
        val tbSystem = findViewById<ToggleButton>(R.id.tb_system)
        val tvToggleStatus = findViewById<TextView>(R.id.tv_toggle_status)
        val layoutDemo = findViewById<LinearLayout>(R.id.layout_demo)

        // Basic ToggleButton
        tbBasic.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            tvToggleStatus.text = "Status Basic: $status"
            showToast("Toggle Basic: $status")
        }

        // Custom ToggleButton
        tbCustom.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tbCustom.setBackgroundColor(Color.GREEN)
                tbCustom.text = "AKTIF"
                tbCustom.setTextColor(Color.WHITE)
            } else {
                tbCustom.setBackgroundColor(Color.RED)
                tbCustom.text = "NON-AKTIF"
                tbCustom.setTextColor(Color.WHITE)
            }
        }

        // System Control Toggle
        tbSystem.setOnCheckedChangeListener { _, isChecked ->
            isSystemOn = isChecked
            updateSystemStatus()
        }

        // Toggle Group (mutually exclusive)
        val tbOption1 = findViewById<ToggleButton>(R.id.tb_option1)
        val tbOption2 = findViewById<ToggleButton>(R.id.tb_option2)
        val tbOption3 = findViewById<ToggleButton>(R.id.tb_option3)

        val toggleGroup = listOf(tbOption1, tbOption2, tbOption3)

        toggleGroup.forEach { toggle ->
            toggle.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    // Uncheck others
                    toggleGroup.forEach { other ->
                        if (other != buttonView) {
                            other.isChecked = false
                        }
                    }
                    showToast("${buttonView.text} dipilih")
                }
            }
        }
    }

    private fun setupSwitchButtons() {
        val switchBasic = findViewById<Switch>(R.id.switch_basic)
        val switchTheme = findViewById<Switch>(R.id.switch_theme)
        val switchSound = findViewById<Switch>(R.id.switch_sound)
        val tvSwitchStatus = findViewById<TextView>(R.id.tv_switch_status)
        val layoutTheme = findViewById<LinearLayout>(R.id.layout_theme)

        // Basic Switch
        switchBasic.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            tvSwitchStatus.text = "Status Switch: $status"
        }

        // Theme Switch
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            isThemeDark = isChecked
            if (isChecked) {
                layoutTheme.setBackgroundColor(Color.DKGRAY)
                switchTheme.setTextColor(Color.WHITE)
                switchTheme.text = "Dark Theme"
            } else {
                layoutTheme.setBackgroundColor(Color.WHITE)
                switchTheme.setTextColor(Color.BLACK)
                switchTheme.text = "Light Theme"
            }
        }

        // Sound Switch with compound button
        switchSound.setOnCheckedChangeListener { _, isChecked ->
            isSoundOn = isChecked
            val icon = if (isChecked) {
                switchSound.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_volume_on, 0, 0, 0
                )
                "ON"
            } else {
                switchSound.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_volume_off, 0, 0, 0
                )
                "OFF"
            }
            switchSound.text = "Sound: $icon"
            showToast("Sound: $icon")
        }

        // Reset All Button
        val btnResetAll = findViewById<Button>(R.id.btn_reset_all)
        btnResetAll.setOnClickListener {
            // Reset ToggleButtons
            findViewById<ToggleButton>(R.id.tb_basic).isChecked = false
            findViewById<ToggleButton>(R.id.tb_custom).isChecked = false
            findViewById<ToggleButton>(R.id.tb_system).isChecked = false

            // Reset ToggleGroup
            listOf(
                findViewById<ToggleButton>(R.id.tb_option1),
                findViewById<ToggleButton>(R.id.tb_option2),
                findViewById<ToggleButton>(R.id.tb_option3)
            ).forEach { it.isChecked = false }

            // Reset Switches
            switchBasic.isChecked = false
            switchTheme.isChecked = false
            switchSound.isChecked = true

            updateSystemStatus()
            showToast("Semua toggle direset")
        }
    }

    private fun updateSystemStatus() {
        val tvSystemStatus = findViewById<TextView>(R.id.tv_system_status)
        val status = if (isSystemOn) {
            "SISTEM: AKTIF \n• Semua fungsi berjalan normal\n• Monitoring aktif\n• Laporan real-time"
        } else {
            "SISTEM: NON-AKTIF \n• Sistem dalam mode standby\n• Monitoring terbatas\n• Laporan periodik"
        }
        tvSystemStatus.text = status
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}