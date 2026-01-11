package com.example.latihanlibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.latihanlibrary.R
import com.example.latihanlibrary.model.User

class UserAdapter(
    private var listUser: List<User>,
    private val listener: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvEmail: TextView = view.findViewById(R.id.tv_email)
        val imgAvatar: ImageView = view.findViewById(R.id.img_avatar)
    }

    fun setData(newList: List<User>) {
        listUser = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        // Menampilkan Nama dan Email
        holder.tvName.text = "${user.first_name} ${user.last_name}"
        holder.tvEmail.text = user.email

        // Menampilkan Gambar dengan Glide (VERSI LENGKAP)
        Glide.with(holder.itemView.context)
            .load(user.avatar) // Mengambil URL gambar dari internet
            .placeholder(R.drawable.ic_menu_gallery) // Gambar sementara saat loading
            .error(R.drawable.ic_menu_gallery)    // Gambar jika internet error (Tanda Seru)
            .circleCrop() // Membuat gambar jadi bulat
            .into(holder.imgAvatar) // Dimasukkan ke ImageView

        // Logika ketika item diklik
        holder.itemView.setOnClickListener { listener(user) }
    }


    override fun getItemCount(): Int = listUser.size
}
