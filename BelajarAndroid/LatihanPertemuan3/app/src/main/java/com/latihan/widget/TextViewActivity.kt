package com.latihan.widget

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.latihan.latihanpertemuan3.R

class TextViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_text_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inisialisasi komponen inputan
        val editTextUser = findViewById<EditText>(R.id.edit_text_user)
        val editTextPassword = findViewById<EditText>(R.id.edit_text_password)
        val editTextEmail = findViewById<EditText>(R.id.edit_text_email)
        val editTextPhone = findViewById<EditText>(R.id.edit_text_phone)
        val buttonSimpan = findViewById<Button>(R.id.button_simpan)

        buttonSimpan.setOnClickListener {
            val userName = editTextUser.text.toString();
            val password = editTextPassword.text.toString();
            val email = editTextEmail.text.toString();
            val telpon = editTextPhone.text.toString();

            if(userName.isEmpty() || password.isEmpty() || email.isEmpty() || telpon.isEmpty()) {
                Toast.makeText(
                    this,
                    "Ada Inputan yang Masih Kosong!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Data Valid = User "+userName+" Pwd "+password+
                            " Email "+email+" Telpon "+telpon,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}