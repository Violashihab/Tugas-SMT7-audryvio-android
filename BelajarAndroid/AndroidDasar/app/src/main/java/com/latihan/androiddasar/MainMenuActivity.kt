package com.latihan.androiddasar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Setup button listeners
        setupButtonListeners()

    }

    private fun setupButtonListeners() {
        // Button untuk Login
        findViewById<Button>(R.id.button_login).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Button untuk Register
        findViewById<Button>(R.id.button_register).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Button lainnya akan diimplementasikan nanti
        findViewById<Button>(R.id.button_textview).setOnClickListener {
            val intent = Intent(this, TextViewActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_dasar).setOnClickListener {
            val intent = Intent(this, ButtonActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_radiobutton).setOnClickListener {
            val intent = Intent(this, RadioButtonActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_checkbox).setOnClickListener {
            val intent = Intent(this, CheckBoxActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_edittext_imageview).setOnClickListener {
            val intent = Intent(this, EditTextImageViewActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_togglebutton).setOnClickListener {
            val intent = Intent(this, ToggleButtonActivity::class.java)
            startActivity(intent)
        }
    }

}