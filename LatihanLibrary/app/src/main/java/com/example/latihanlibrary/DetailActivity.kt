package com.example.latihanlibrary

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.latihanlibrary.model.User // Import harus ke lokasi baru

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra<User>("USER_DATA")

        if (user != null) {
            val tvName: TextView = findViewById(R.id.tv_detail_name)
            val tvEmail: TextView = findViewById(R.id.tv_detail_email)
            val imgDetail: ImageView = findViewById(R.id.img_detail)

            tvName.text = "${user.first_name} ${user.last_name}"
            tvEmail.text = user.email
            Glide.with(this).load(user.avatar).into(imgDetail)
        }
    }
}
