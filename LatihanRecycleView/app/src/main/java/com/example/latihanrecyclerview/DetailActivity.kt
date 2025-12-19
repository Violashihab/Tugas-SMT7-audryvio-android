package com.example.latihanrecyclerview

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val imgDetailPhoto: ImageView = findViewById(R.id.img_detail_photo)
        val tvDetailName: TextView = findViewById(R.id.tv_detail_name)
        val tvDetailDescription: TextView = findViewById(R.id.tv_detail_description)


        val nama = intent.getStringExtra("EXTRA_NAMA")
        val deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI")
        val foto = intent.getIntExtra("EXTRA_FOTO", 0)


        tvDetailName.text = nama
        tvDetailDescription.text = deskripsi

        if (foto != 0) {
            imgDetailPhoto.setImageResource(foto)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail $nama"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}