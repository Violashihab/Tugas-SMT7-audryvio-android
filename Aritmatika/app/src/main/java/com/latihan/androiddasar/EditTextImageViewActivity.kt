package com.latihan.androiddasar

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditTextImageViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_text_image_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEditTexts()
        setupImageViews()
    }

    private fun setupEditTexts() {
        val etBasic = findViewById<EditText>(R.id.et_basic)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etMultiLine = findViewById<EditText>(R.id.et_multiline)
        val etWithCounter = findViewById<EditText>(R.id.et_with_counter)
        val tvCharCount = findViewById<TextView>(R.id.tv_char_count)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvResult = findViewById<TextView>(R.id.tv_result)

        // Real-time character counter
        etWithCounter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val count = s?.length ?: 0
                tvCharCount.text = "$count/50 karakter"

                // Change color based on length
                if (count > 40) {
                    tvCharCount.setTextColor(Color.RED)
                } else if (count > 30) {
                    tvCharCount.setTextColor(Color.YELLOW)
                } else {
                    tvCharCount.setTextColor(Color.GRAY)
                }
            }
        })

        // Input validation for email
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.error = "Format email tidak valid"
                } else {
                    etEmail.error = null
                }
            }
        })

        // Password strength indicator
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                val strength = when {
                    password.isEmpty() -> ""
                    password.length < 4 -> "Lemah"
                    password.length < 8 -> "Sedang"
                    else -> "Kuat"
                }
                etPassword.hint = "Password ($strength)"
            }
        })

        // Submit button
        btnSubmit.setOnClickListener {
            val result = """
                DATA YANG DIINPUT:
                
                Nama: ${etBasic.text}
                Email: ${etEmail.text}
                Password: ${"*".repeat(etPassword.text.length)}
                Telepon: ${etPhone.text}
                Pesan: ${etMultiLine.text}
                Bio: ${etWithCounter.text}
            """.trimIndent()

            tvResult.text = result

            // Clear all fields after submit
            if (etBasic.text.isNotEmpty()) {
                etBasic.text.clear()
                etEmail.text.clear()
                etPassword.text.clear()
                etPhone.text.clear()
                etMultiLine.text.clear()
                etWithCounter.text.clear()
                showToast("Data berhasil disimpan!")
            }
        }

        // Clear button
        val btnClear = findViewById<Button>(R.id.btn_clear)
        btnClear.setOnClickListener {
            etBasic.text.clear()
            etEmail.text.clear()
            etPassword.text.clear()
            etPhone.text.clear()
            etMultiLine.text.clear()
            etWithCounter.text.clear()
            tvResult.text = "Hasil akan ditampilkan di sini..."
            showToast("Semua input dihapus")
        }
    }

    private fun setupImageViews() {
        val ivBasic = findViewById<ImageView>(R.id.iv_basic)
        val ivScaleType = findViewById<ImageView>(R.id.iv_scale_type)
        val ivClickable = findViewById<ImageView>(R.id.iv_clickable)
        val tvImageInfo = findViewById<TextView>(R.id.tv_image_info)
        val btnChangeImage = findViewById<Button>(R.id.btn_change_image)
        val rgScaleType = findViewById<RadioGroup>(R.id.rg_scale_type)

        var currentImage = 1

        // Clickable ImageView
        ivClickable.setOnClickListener {
            showToast("ImageView diklik!")
            // Change color when clicked
            ivClickable.setColorFilter(Color.argb(150, 0, 255, 0))
        }

        // Change Image button
        btnChangeImage.setOnClickListener {
            currentImage = if (currentImage == 1) 2 else 1
            val imageResource = if (currentImage == 1) R.drawable.ic_android else R.drawable.ic_code
            ivBasic.setImageResource(imageResource)
            tvImageInfo.text = "Gambar: ${if (currentImage == 1) "Android" else "Code"}"
            showToast("Gambar diubah!")
        }

        // ScaleType RadioGroup
        rgScaleType.setOnCheckedChangeListener { _, checkedId ->
            val scaleType = when (checkedId) {
                R.id.rb_center -> ImageView.ScaleType.CENTER
                R.id.rb_center_crop -> ImageView.ScaleType.CENTER_CROP
                R.id.rb_center_inside -> ImageView.ScaleType.CENTER_INSIDE
                R.id.rb_fit_center -> ImageView.ScaleType.FIT_CENTER
                R.id.rb_fit_xy -> ImageView.ScaleType.FIT_XY
                else -> ImageView.ScaleType.CENTER
            }
            ivScaleType.scaleType = scaleType
            tvImageInfo.text = "ScaleType: ${scaleType.name}"
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}