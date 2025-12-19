package com.example.latihanrecyclerview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvWayang: RecyclerView
    private val list = ArrayList<Wayang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvWayang = findViewById(R.id.rv_wayang)
        rvWayang.setHasFixedSize(true)

        list.addAll(getListWayang())

        showRecyclerList()
    }

    private fun getListWayang(): ArrayList<Wayang> {
        val dataName = arrayOf("Yudistira", "Bima", "Arjuna", "Nakula", "Sadewa")

        val dataDescription = arrayOf(
            "Anak tertua Pandawa, dikenal jujur dan bijaksana.",
            "Anak kedua, memiliki kekuatan fisik yang luar biasa.",
            "Anak ketiga, pemanah ulung dan paras rupawan.",
            "Anak keempat (kembar), ahli pengobatan dan berkuda.",
            "Anak kelima (kembar), bijaksana dan ahli perbintangan."
        )


        val dataPhoto = arrayOf(
            R.drawable.yudistira,
            R.drawable.bima,
            R.drawable.arjuna,
            R.drawable.nakula,
            R.drawable.sadewa
        )

        val listWayang = ArrayList<Wayang>()
        for (i in dataName.indices) {
            val wayang = Wayang(dataName[i], dataDescription[i], dataPhoto[i])
            listWayang.add(wayang)
        }
        return listWayang
    }

    private fun showRecyclerList() {
        rvWayang.layoutManager = GridLayoutManager(this, 2)

        val listWayangAdapter = ListWayangAdapter(list, this)
        rvWayang.adapter = listWayangAdapter
    }
}