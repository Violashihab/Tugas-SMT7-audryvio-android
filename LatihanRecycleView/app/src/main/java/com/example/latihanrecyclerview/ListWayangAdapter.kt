package com.example.latihanrecyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListWayangAdapter(
    private val listWayang: ArrayList<Wayang>,
    private val context: Context
) : RecyclerView.Adapter<ListWayangAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wayang, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val wayang = listWayang[position]

        holder.tvName.text = wayang.nama
        holder.tvDescription.text = wayang.deskripsi
        holder.imgPhoto.setImageResource(wayang.foto)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra("EXTRA_NAMA", wayang.nama)
            intent.putExtra("EXTRA_DESKRIPSI", wayang.deskripsi)
            intent.putExtra("EXTRA_FOTO", wayang.foto)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listWayang.size
}